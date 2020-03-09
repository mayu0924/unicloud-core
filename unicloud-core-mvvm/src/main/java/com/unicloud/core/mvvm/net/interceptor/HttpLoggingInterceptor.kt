package com.unicloud.core.mvvm.net.interceptor

import android.text.TextUtils
import android.util.Log
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.JsonUtils
import com.blankj.utilcode.util.LogUtils
import com.unicloud.core.mvvm.net.BaseRetrofitClient
import com.unicloud.core.mvvm.net.BaseRetrofitClient.Companion.CUSTOM_REPEAT_REQ_PROTOCOL
import com.unicloud.core.mvvm.net.interceptor.log.Level
import com.unicloud.core.mvvm.net.interceptor.log.Printer
import okhttp3.*
import okhttp3.internal.platform.Platform
import okhttp3.internal.platform.Platform.INFO
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * 借鉴其他Demo里的日志打印
 * #当日志比较多时，有时候会出现输出不全的情况
 */
class HttpLoggingInterceptor : Interceptor {

    private var tag: String = "HttpLogging"
    var isDebug: Boolean = false
    var type = INFO
    var requestTag: String = tag
    var responseTag: String = tag
    var level = Level.BASIC
    private val headers = Headers.Builder()
    var logger: Logger? = null


    interface Logger {
        fun log(level: Int, tag: String, msg: String)

        companion object {
            val DEFAULT: Logger = object : Logger {
                override fun log(level: Int, tag: String, msg: String) {
                    Platform.get().log(level, msg, null)
                }
            }
        }
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (getHeaders().size() > 0) {
            val headers = request.headers()
            val names = headers.names()
            val iterator = names.iterator()
            val requestBuilder = request.newBuilder()
            requestBuilder.headers(getHeaders())
            while (iterator.hasNext()) {
                val name = iterator.next()
                requestBuilder.addHeader(name, headers.get(name)!!)
            }
            request = requestBuilder.build()
        }

        if (!isDebug || level == Level.NONE) {
            return chain.proceed(request)
        }
        val requestBody = request.body()

        var rContentType: MediaType? = null
        if (requestBody != null) {
            rContentType = request.body()!!.contentType()
        }

        var rSubtype: String? = null
        if (rContentType != null) {
            rSubtype = rContentType.subtype()
        }

        if (rSubtype != null && (rSubtype.contains("json")
                    || rSubtype.contains("xml")
                    || rSubtype.contains("plain")
                    || rSubtype.contains("html"))
        ) {
            Printer.printJsonRequest(this, request)
        } else {
            Printer.printFileRequest(this, request)
        }

        val st = System.nanoTime()
//        val response = chain.proceed(request)

        //==========================================================================================
        //获取请求的KEY ，以便后面去除
        val requestKey: String = EncryptUtils.encryptMD5ToString(request.toString())
        val response: Response //准备返回Response
        response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            LogUtils.eTag("<-- HTTP FAILED: $e")
            //异常的返回也是完成Http请求。在这里移除请求登记
            if (!TextUtils.isEmpty(e.toString()) && e.toString().contains(CUSTOM_REPEAT_REQ_PROTOCOL)) {
                LogUtils.iTag("REPEAT-REQUEST", "remove(requestKey)1     " + Thread.currentThread().name)
            } else {
                BaseRetrofitClient.requestKeyMap.remove(requestKey)
            }
            throw e
        }
        BaseRetrofitClient.requestKeyMap.remove(requestKey) //在这里移除正常的请求登记
        LogUtils.iTag("REPEAT-REQUEST", "remove(requestKey)     " + Thread.currentThread().name)

        //==========================================================================================

        val segmentList = request.url().encodedPathSegments()
        val chainMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - st)
        val header = response.headers().toString()
        val code = response.code()
        val isSuccessful = response.isSuccessful
        val responseBody = response.body()
        val contentType = responseBody!!.contentType()

        var subtype: String? = null
        val body: ResponseBody

        if (contentType != null) {
            subtype = contentType.subtype()
        }

        if (subtype != null && (subtype.contains("json")
                    || subtype.contains("xml")
                    || subtype.contains("plain")
                    || subtype.contains("html"))
        ) {
            val bodyString = responseBody.string()
            val bodyJson = JsonUtils.formatJson(bodyString)
            Printer.printJsonResponse(
                this,
                chainMs,
                isSuccessful,
                code,
                header,
                bodyJson,
                segmentList
            )
            body = ResponseBody.create(contentType, bodyString)
        } else {
            Printer.printFileResponse(this, chainMs, isSuccessful, code, header, segmentList)
            return response
        }
        return response.newBuilder().body(body).build()
    }

    private fun getHeaders(): Headers = headers.build()
}
