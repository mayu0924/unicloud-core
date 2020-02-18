package com.unicloud.core.demo.activity.vm

import android.os.Environment
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.unicloud.core.demo.model.bean.ArticleListBean
import com.unicloud.core.demo.model.repository.MainRepository
import com.unicloud.core.demo.net.RetrofitClient
import com.unicloud.core.mvvm.BaseViewModel
import com.unicloud.core.mvvm.event.Message
import com.unicloud.core.mvvm.net.upload.ProgressListener
import com.unicloud.core.mvvm.net.upload.UploadFileRequestBody
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class MainViewModel : BaseViewModel() {
    private val mainRespository by lazy { MainRepository() }
    val mArticleListBean: MutableLiveData<ArticleListBean> = MutableLiveData()

    fun getHomeArticles() {
        launchOnlyresult({
            mainRespository.getHomeArticles(0)
        }, {
            mArticleListBean.value = it!!
            defUI.toastEvent.postValue(it.datas[0].title)
            defUI.msgEvent.postValue(Message(0, "请求成功", it.datas[0].title))
        })
    }

    fun download() {
        val url =
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581411390581&di=bfb76c9e2fc467e00f2c8935402b8c07&imgtype=0&src=http%3A%2F%2Fimage.xcar.com.cn%2Fattachments%2Fa%2Fday_130226%2F2013022601_1ec6030ef11ef4fdf718ear1bVBZNRx5.jpg"
        val newFilePath =
            "${Environment.getExternalStorageDirectory().path}/download/${url.substringAfterLast("/")}"
        val call = RetrofitClient.baseService.downloadFile(
            RetrofitUrlManager.getInstance().setUrlNotChange(url)
        )
        downloadFile(call, newFilePath, { curr, total ->
            LogUtils.dTag("download", "curr:$curr,  total:$total")
        }, { file ->
            LogUtils.dTag("download", "newFile:${file.absolutePath}")
        }, {errMsg ->
            ToastUtils.showShort(errMsg)
        })
    }

    fun upload() {
        val url =
            "http://hd215.api.yesapi.cn/?s=App.CDN.UploadImg&app_key=B78EEC920E196C11F4FA1B6765BD5035&sign=BDD0E0926C0B27C0D10B9B8474BF970D"
        val filePart = MultipartBody.Part.createFormData(
            "file", "a.jpg",
            UploadFileRequestBody(RequestBody.create(MediaType.parse("application/octet-stream"), File.createTempFile("abc", "txt")), object : ProgressListener{
                override fun onProgress(hasWrittenLen: Long, totalLen: Long, hasFinish: Boolean) {

                }
            })// image/jpg  application/octet-stream   multipart/form-data;charset=UTF-8
        )
        val call = RetrofitClient.baseService.uploadFile(
            RetrofitUrlManager.getInstance().setUrlNotChange(url), filePart
        )
        uploadFile(call, {
            ToastUtils.showShort(it)
        }, {
            ToastUtils.showShort(it)
        })
    }

    fun uploadMultiFile() {
        val url =
            "http://10.0.54.183:14500/api/ziguang-doc/api/v1/docServer/file/withTagId"
        val paramMap: MutableMap<String, RequestBody> = mutableMapOf()
        addMultiPart(paramMap, "a", File.createTempFile("abc", "txt"))
        addMultiPart(paramMap, "b", File.createTempFile("abc", "txt"))
        val call = RetrofitClient.baseService.uploadMultiFile(
            RetrofitUrlManager.getInstance().setUrlNotChange(url), paramMap
        )
        uploadFile(call, {
            ToastUtils.showShort(it)
        }, {
            ToastUtils.showShort(it)
        })
    }
}