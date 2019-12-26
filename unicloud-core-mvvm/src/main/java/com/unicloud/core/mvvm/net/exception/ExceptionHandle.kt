package com.unicloud.core.mvvm.net.exception

import android.net.ParseException
import android.text.TextUtils
import com.blankj.utilcode.util.JsonUtils
import com.google.gson.JsonParseException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException

object ExceptionHandle {
    fun handleException(e: Throwable): ResponseThrowable {
        val ex: ResponseThrowable
        if(e is ResponseThrowable){
            ex = e
        }else if (e is HttpException) {
            if (e.code() == 401) {
                ex = ResponseThrowable(ERROR.TOKEN_ERROR)
            } else {
                val resp = e.response()
                val json = resp?.errorBody()?.string()
                if (!TextUtils.isEmpty(json)) {
                    val code = JsonUtils.getInt(json, "status")
                    val message = JsonUtils.getString(json, "message")
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
            ex = if (e.message.isNullOrEmpty()) ResponseThrowable(ERROR.UNKNOWN.getKey(), e.message!!)
            else ResponseThrowable(ERROR.UNKNOWN)
        }
        return ex
    }
}