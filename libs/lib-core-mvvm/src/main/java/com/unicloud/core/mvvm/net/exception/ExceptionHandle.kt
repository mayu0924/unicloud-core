package com.unicloud.core.mvvm.net.exception

import android.net.ParseException
import android.text.TextUtils
import com.blankj.utilcode.util.JsonUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.JsonParseException
import com.unicloud.core.mvvm.net.BaseRetrofitClient
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException

object ExceptionHandle {
    fun handleException(e: Throwable): ResponseThrowable {
        val ex: ResponseThrowable
        if (e is ResponseThrowable) {
            ex = e
        } else if (e is HttpException) {
            if (e.code() == 401) {
                ex = ResponseThrowable(ERROR.TOKEN_ERROR)
            } else {
                val resp = e.response()
                val json = resp?.errorBody()?.string()
                var message = ""
                if (!TextUtils.isEmpty(json)) {
                    val code = try {
                        val jsonObject = JSONObject(json!!)
                        message = JsonUtils.getString(json, "message")
                        when {
                            jsonObject.has("code") -> JsonUtils.getInt(json, "code")
                            jsonObject.has("status") -> JsonUtils.getInt(json, "status")
                            else -> -1
                        }
                    } catch (e2: Exception) {
                        message = e.message()
                        e.code()
                    }
                    ex = ResponseThrowable(code, message)
                } else {
                    ex = ResponseThrowable(e.code(), e.message())
                }
            }
        } else if (e is JsonParseException
            || e is JSONException
            || e is ParseException || e is com.google.gson.stream.MalformedJsonException
        ) {
            ex = ResponseThrowable(ERROR.PARSE_ERROR)
        } else if (e is ConnectException) {
            ex = ResponseThrowable(ERROR.NETWORD_ERROR)
        } else if (e is javax.net.ssl.SSLException) {
            ex = ResponseThrowable(ERROR.SSL_ERROR)
        } else if (e is ConnectTimeoutException) {
            ex = ResponseThrowable(ERROR.TIMEOUT_ERROR)
        } else if (e is java.net.SocketTimeoutException) {
            ex = ResponseThrowable(ERROR.TIMEOUT_ERROR)
        } else if (e is java.net.UnknownHostException) {
            ex = ResponseThrowable(ERROR.TIMEOUT_ERROR)
        } else {
            ex = if (null != e.message) {
                if (e.message!!.contains(BaseRetrofitClient.CUSTOM_REPEAT_REQ_PROTOCOL)) {
                    ResponseThrowable(ERROR.REPEAT_REQUEST)
                } else {
                    ResponseThrowable(ERROR.UNKNOWN.getKey(), e.message!!)
                }
            } else {
                ResponseThrowable(ERROR.UNKNOWN)
            }
        }
        return ex
    }
}