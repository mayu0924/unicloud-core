package com.unicloud.core.mvvm

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.Utils
import com.unicloud.core.mvvm.event.Message
import com.unicloud.core.mvvm.event.SingleLiveEvent
import com.unicloud.core.mvvm.net.BaseResult
import com.unicloud.core.mvvm.net.exception.ExceptionHandle
import com.unicloud.core.mvvm.net.exception.ResponseThrowable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import java.io.File
import java.io.InputStream
import java.io.OutputStream


open class BaseViewModel : AndroidViewModel(Utils.getApp()), LifecycleObserver {
    val defUI: UIChange by lazy { UIChange() }

    /**
     * 所有网络请求都在 viewModelScope 域中启动，当页面销毁时会自动
     * 调用ViewModel的  #onCleared 方法取消所有协程
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    /**
     * 用流的方式进行网络请求
     */
    fun <T> launchFlow(block: suspend () -> T): Flow<T> {
        return flow {
            emit(block())
        }
    }

    /**
     *  不过滤请求结果
     * @param block 请求体
     * @param error 失败回调
     * @param complete  完成回调（无论成功失败都会调用）
     * @param isShowDialog 是否显示加载框
     */
    fun launch(
        block: suspend CoroutineScope.() -> Unit,
        error: suspend CoroutineScope.(ResponseThrowable) -> Unit = {
            defUI.toastEvent.postValue("${it.code}:${it.errMsg}")
        },
        complete: suspend CoroutineScope.() -> Unit = {},
        isShowDialog: Boolean = true
    ) {
        if (isShowDialog) defUI.showDialog.call()
        launchUI {
            handleException(
                withContext(Dispatchers.IO) { block },
                { error(it) },
                {
                    defUI.dismissDialog.call()
                    complete()
                }
            )
        }
    }

    /**
     * 过滤请求结果，其他全抛异常
     * @param block 请求体
     * @param success 成功回调
     * @param errorCall 失败回调
     * @param complete  完成回调（无论成功失败都会调用）
     * @param isShowDialog 是否显示加载框
     */
    fun <T> launchOnlyresult(
        block: suspend CoroutineScope.() -> BaseResult<T>,
        success: (T?) -> Unit,
        error: (ResponseThrowable) -> Unit = {
            defUI.toastEvent.postValue("${it.code}:${it.errMsg}")
        },
        complete: () -> Unit = {},
        isShowDialog: Boolean = true
    ) {
        if (isShowDialog) defUI.showDialog.call()
        launchUI {
            handleException(
                { withContext(Dispatchers.IO) { block() } },
                { res ->
                    executeResponse(res) { success(it) }
                },
                {
                    error(it)
                },
                {
                    defUI.dismissDialog.call()
                    complete()
                }
            )
        }
    }

    /**
     * 添加多媒体类型
     *
     * @param paramMap 参数对
     * @param key      键
     * @param obj      值
     */
    fun addMultiPart(
        paramMap: MutableMap<String, RequestBody>,
        key: String,
        obj: Any
    ) {
        if (obj is String) {
            val body = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), obj)
            paramMap[key] = body
        } else if (obj is File) {
            val body = RequestBody.create(MediaType.parse("multipart/form-data;charset=UTF-8"), obj)
            paramMap[key + "\"; filename=\"" + obj.name + ""] = body
        }
    }

    fun uploadFile(
        call: Call<ResponseBody>,
        success: (result: String) -> Unit,
        fail: (errMsg: String) -> Unit
    ) {
        launchUI {
            withContext(Dispatchers.IO) {
                val response = call.execute()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    success.invoke(body.string())
                } else {
                    fail.invoke("上传失败[${response.code()}]: ${response.errorBody()?.string()}")
                }
            }
        }
    }

    fun downloadFile(
        call: Call<ResponseBody>,
        filePath: String,
        progress: (curr: Long, total: Long) -> Unit,
        complete: (file: File) -> Unit,
        fail: (errMsg: String) -> Unit
    ) {
        launchUI {
            withContext(Dispatchers.IO) {
                val file = File(filePath)
                file.createNewFile()
                //通过GitHubService.getInstance()可以直接拿到GitHubService对象
                val response = call.execute()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    var inStream: InputStream? = null
                    var outStream: OutputStream? = null
                    /*注意,在kotlin中没有受检异常,
                    如果这里不写try catch,编译器也是不会报错的,
                    但是我们需要确保流关闭,所以需要在finally进行操作*/
                    try {
                        //以下读写文件的操作和java类似
                        inStream = body.byteStream()
                        outStream = file.outputStream()
                        //文件总长度
                        val contentLength = body.contentLength()
                        //当前已下载长度
                        var currentLength = 0L
                        //缓冲区
                        val buff = ByteArray(1024)
                        var len = inStream.read(buff)
                        var percent = 0
                        while (len != -1) {
                            outStream.write(buff, 0, len)
                            currentLength += len
                            /*不要频繁的调用切换线程,否则某些手机可能因为频繁切换线程导致卡顿,
                            这里加一个限制条件,只有下载百分比更新了才切换线程去更新UI*/
                            if ((currentLength * 100 / contentLength).toInt() > percent) {
                                percent = (currentLength / contentLength * 100).toInt()
                                //切换到主线程更新UI
                                withContext(Dispatchers.Main) {
                                    progress.invoke(currentLength, contentLength)
                                }
                                //更新完成UI之后立刻切回IO线程
                            }
                            len = inStream.read(buff)
                        }
                        //下载完成之后,切换到主线程更新UI
                        withContext(Dispatchers.Main) {
                            complete.invoke(file)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        fail.invoke("下载失败")
                    } finally {
                        inStream?.close()
                        outStream?.close()
                    }
                }
            }
        }
    }

    /**
     * 请求结果过滤
     */
    private suspend fun <T> executeResponse(
        response: BaseResult<T>,
        success: suspend CoroutineScope.(T?) -> Unit
    ) {
        coroutineScope {
            if (response.isSuccess()) success(response.data())
            else throw ResponseThrowable(response.code(), response.message())
        }
    }

    /**
     * 异常统一处理
     */
    private suspend fun <T> handleException(
        block: suspend CoroutineScope.() -> BaseResult<T>,
        success: suspend CoroutineScope.(BaseResult<T>) -> Unit,
        error: suspend CoroutineScope.(ResponseThrowable) -> Unit,
        complete: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                success(block())
            } catch (e: Throwable) {
                error(ExceptionHandle.handleException(e))
            } finally {
                complete()
            }
        }
    }


    /**
     * 异常统一处理
     */
    private suspend fun handleException(
        block: suspend CoroutineScope.() -> Unit,
        error: suspend CoroutineScope.(ResponseThrowable) -> Unit,
        complete: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                block()
            } catch (e: Throwable) {
                error(ExceptionHandle.handleException(e))
            } finally {
                complete()
            }
        }
    }

    /**
     * UI事件
     */
    inner class UIChange {
        val showDialog by lazy { SingleLiveEvent<String>() }
        val dismissDialog by lazy { SingleLiveEvent<Void>() }
        val toastEvent by lazy { SingleLiveEvent<String>() }
        val msgEvent by lazy { SingleLiveEvent<Message>() }
    }
}