package com.unicloud.core.net

import android.annotation.SuppressLint
import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.unicloud.core.net.interceptor.HttpCacheInterceptor
import com.unicloud.core.net.interceptor.HttpHeaderInterceptor
import com.unicloud.core.net.interceptor.HttpLoggingInterceptor
import com.unicloud.core.net.interceptor.HttpProgressInterceptor
import com.unicloud.core.net.interceptor.log.Level
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

abstract class BaseRetrofitClient {
    companion object {
        var CONTEXT: Context? = null
        private const val TIME_OUT = 5.toLong()
        private const val READ_TIME_OUT = 15.toLong()
    }

    private val client: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor().apply {
            isDebug = isDebug()
            level = Level.BASIC
            type = Platform.INFO
            requestTag = "Request"
            requestTag = "Response"
        }
        val header = HttpHeaderInterceptor(defaultHeader())
        val progress = HttpProgressInterceptor()
        val cache = HttpCacheInterceptor(CONTEXT!!, 0, 0)
        // =============================================================
        builder.addInterceptor(header)
            .addInterceptor {
                it.proceed(handleRequest(it.request()))
            }
//            .addInterceptor(progress)
            .addInterceptor(cache)
            .addNetworkInterceptor(cache)
            .addInterceptor(logging)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)

        if (isUseCookie()) {
            val cookieJar =
                PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(CONTEXT))
            builder.cookieJar(cookieJar)
        }

        if (isUseSSL()) {
            builder.sslSocketFactory(getSSLSocketFactory())
                .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
        }
        handleBuilder(builder)
        builder.build()
    }

    abstract fun isDebug():Boolean

    /**
     * 自定义请求设置
     */
    abstract fun handleBuilder(builder: OkHttpClient.Builder)

    /**
     * 自定义请求头部
     */
    abstract fun defaultHeader(): MutableMap<String, String>?

    /**
     * 自定义请求【添加、修改、拦截参数】
     */
    abstract fun handleRequest(request: Request): Request

    /**
     * 是否使用cookie
     */
    abstract fun isUseCookie(): Boolean

    /**
     * 是否使用SSL证书
     */
    abstract fun isUseSSL(): Boolean

    fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build().create(serviceClass)
    }

    private fun getSSLSocketFactory(): SSLSocketFactory {
        //创建一个不验证证书链的证书信任管理器。
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            @Throws(CertificateException::class)
            override fun checkClientTrusted(
                chain: Array<java.security.cert.X509Certificate>,
                authType: String
            ) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            @Throws(CertificateException::class)
            override fun checkServerTrusted(
                chain: Array<java.security.cert.X509Certificate>,
                authType: String
            ) {
            }

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }
        })

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(
            null, trustAllCerts,
            SecureRandom()
        )
        // Create an ssl socket factory with our all-trusting manager
        return sslContext
            .socketFactory
    }
}