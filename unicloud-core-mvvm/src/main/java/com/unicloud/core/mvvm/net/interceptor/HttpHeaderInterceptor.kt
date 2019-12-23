package com.unicloud.core.mvvm.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HttpHeaderInterceptor(private var mMapHeader: MutableMap<String, String>?) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
        mMapHeader?.forEach {
            builder.header(it.key, it.value)
        }
        val requestBuilder =
            builder.method(originalRequest.method(), originalRequest.body())
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}