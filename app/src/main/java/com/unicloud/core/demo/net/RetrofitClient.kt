package com.unicloud.core.demo.net

import android.content.Context
import android.os.Build
import com.unicloud.core.demo.BuildConfig
import com.unicloud.core.demo.R
import com.unicloud.core.demo.model.api.ApiService
import com.unicloud.core.mvvm.net.BaseRetrofitClient
import com.unicloud.core.mvvm.net.BaseService
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * 网络请求
 */
object RetrofitClient : BaseRetrofitClient() {

    val baseService: BaseService by lazy { getService(BaseService::class.java) }

    val service: ApiService by lazy { getService(ApiService::class.java) }

    private val header: MutableMap<String, String> by lazy {
        mutableMapOf(
            "appName" to (CONTEXT?.resources?.getString(R.string.app_name) ?: "未知应用"),
            "platform" to "android",
            "osVersion" to Build.VERSION.RELEASE,
            "deviceModel" to Build.BRAND + "  " + Build.MODEL,
            "appVersion" to BuildConfig.VERSION_NAME,
            "versionCode" to "${BuildConfig.VERSION_CODE}",
            "workspaceId" to "1",
            "Authorization" to "bearereyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.bearereyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNTgxND.p1T6uZGozIP3yVYspj_plZm10OWURr061u91yXX0fgYEyNDI3LCJhdXRob3JpdGllcyI6WyJ3cml0ZSIsInJlYWQiXSwianRpIjoiMWQ5YzU3M2EtODdhZi00ZTFlLTgwNTAtNGZjNDEyNmJkN2M2IiwiY2xpZW50X2lkIjoiMTU3OTQzMDUxMTkwNyJ9.Ib9c3dluGJLR63tWiJP2E7JBjMwUOLrKfpWr7I9GS6c"
        )
    }

    fun init(context: Context) {
        CONTEXT = context
    }

    override fun baseUrl() = "https://www.unicloud.com"

    override fun isDebug(): Boolean = BuildConfig.DEBUG

    override fun handleBuilder(builder: OkHttpClient.Builder) {
    }

    override fun handleRequest(request: Request): Request {
        return request.newBuilder()
            .url(
                request
                    .url()
                    .newBuilder()
//                    .addEncodedQueryParameter("test", "123")
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
}