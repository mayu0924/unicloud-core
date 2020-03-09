package com.unicloud.core.mvvm.net.interceptor

import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.LogUtils
import com.unicloud.core.mvvm.net.BaseRetrofitClient
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import java.io.IOException

class HttpHeaderInterceptor(private var mMapHeader: MutableMap<String, String>?) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        //拦截处理重复的HTTP 请求,类似 防止快速点击按钮去重 可以不去处理了，全局统一处理
        val requestKey: String = EncryptUtils.encryptMD5ToString(originalRequest.toString())

        if(BaseRetrofitClient.requestKeyMap.containsKey(requestKey)){
            //如果是重复的请求，抛出一个自定义的错误，这个错误大家根据自己的业务定义吧
            LogUtils.eTag("REPEAT-REQUEST", "重复请求:" + requestKey + "  ---------------重复请求 ----" + Thread.currentThread().getName())
            return Response.Builder()
                .protocol(Protocol.get(BaseRetrofitClient.CUSTOM_REPEAT_REQ_PROTOCOL))
                .request(originalRequest) //multi thread
                .build()
        } else {
            BaseRetrofitClient.requestKeyMap[requestKey] = System.currentTimeMillis()
//            LogUtils.eTag("REPEAT-REQUEST", "注册请求:" + requestKey + " ----  " + Thread.currentThread().name)
        }

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