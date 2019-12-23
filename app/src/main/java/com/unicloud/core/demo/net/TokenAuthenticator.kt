package com.unicloud.core.demo.net

import android.provider.SyncStateContract
import com.blankj.utilcode.util.Utils
import com.unicloud.core.demo.model.api.ApiService
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response?): Request? {
//        val refreshToken = Utils.defaultSP.getString(SyncStateContract.Constants.KEY_REFRESH_TOKEN, null)
//        var newToken: String? = null
//        //这里使用同步的方式去获取新的token
//        if (refreshToken != null) newToken = ApiService.default().refreshToken(refreshToken).execute().body()
//
//        return if (newToken != null) {
//            Utils.defaultSP.edit().putString(SyncStateContract.Constants.KEY_ACCESS_TOKEN, newToken).apply()
//            response?.request()?.newBuilder()?.header("Authentication", newToken)?.build()
//        } else {
//            response?.request()
//        }

        return response?.request()
    }
}