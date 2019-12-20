package com.unicloud.core.net.interceptor

import android.content.Context
import com.unicloud.core.net.util.NetworkUtil
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HttpCacheInterceptor(
    private val mContext: Context,
    private val mCacheTimeWithNet: Int,
    private val mCacheTimeWithoutNet: Int
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //对request的设置是用来指定有网/无网下所走的方式
        //对response的设置是用来指定有网/无网下的缓存时长
        var request = chain.request()
        if (!NetworkUtil.isNetWorkAvailable(mContext)) {
            //无网络下强制使用缓存，无论缓存是否过期,此时该请求实际上不会被发送出去。
            //有网络时则根据缓存时长来决定是否发出请求
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
        }
        val response = chain.proceed(request)
        return if (NetworkUtil.isNetWorkAvailable(mContext)) {
            //有网络情况下，根据请求接口的设置，配置缓存。
            //String cacheControl = request.cacheControl().toString();
            //有网络情况下，超过1分钟，则重新请求，否则直接使用缓存数据
            val maxAge = if (mCacheTimeWithNet > 0) mCacheTimeWithNet else 60 //缓存一分钟
            val cacheControl = "public,max-age=$maxAge"
            //当然如果你想在有网络的情况下都直接走网络，那么只需要
            //将其超时时间maxAge设为0即可
            response.newBuilder().header("Cache-Control", cacheControl)
                .removeHeader("Pragma").build()
        } else { //无网络时直接取缓存数据，该缓存数据保存1周
            val maxStale =
                if (mCacheTimeWithoutNet > 0) mCacheTimeWithoutNet else 60 * 60 * 24 * 7 * 1 //1周
            response.newBuilder()
                .header("Cache-Control", "public,only-if-cached,max-stale=$maxStale")
                .removeHeader("Pragma").build()
        }
    }

}