package com.unicloud.core.demo.net

import android.content.Context
import android.os.Build
import com.unicloud.core.demo.BuildConfig
import com.unicloud.core.demo.R
import com.unicloud.core.demo.model.api.ApiService
import com.unicloud.core.net.BaseRetrofitClient
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * 网络请求
 */
object RetrofitClient : BaseRetrofitClient() {

    val service: ApiService by lazy { getService(ApiService::class.java, ApiService.BASE_URL) }

    private val header: MutableMap<String, String> by lazy {
        mutableMapOf(
            "appName" to (CONTEXT?.resources?.getString(R.string.app_name) ?: "未知应用"),
            "platform" to "android",
            "osVersion" to Build.VERSION.RELEASE,
            "deviceModel" to Build.BRAND + "  " + Build.MODEL,
            "appVersion" to BuildConfig.VERSION_NAME,
            "versionCode" to "${BuildConfig.VERSION_CODE}"
        )
    }

    fun init(context: Context) {
        CONTEXT = context
    }

    override fun isDebug(): Boolean = BuildConfig.DEBUG

    override fun handleBuilder(builder: OkHttpClient.Builder) {

    }

    override fun handleRequest(request: Request): Request {
        return request.newBuilder()
            .url(
                request
                    .url()
                    .newBuilder()
                    .addEncodedQueryParameter("test", "123")
                    .build()
            )
            .build()
    }

    override fun defaultHeader(): MutableMap<String, String>? {
        return header
    }

    override fun isUseCookie(): Boolean {
        return true
    }

    override fun isUseSSL(): Boolean {
        return true
    }

    fun refreshToken(token: String?) {
        if (token.isNullOrEmpty()) {
            header.remove("X-token")
        } else {
            header["X-token"] = token
        }
    }
}