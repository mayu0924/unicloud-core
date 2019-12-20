package com.unicloud.core.net.exception

import android.net.ParseException
import android.util.MalformedJsonException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import java.net.ConnectException

object ExceptionHandle {
    fun handleException(e: Throwable): ResponseThrowable {
        val ex: ResponseThrowable
        if (e is JSONException
            || e is ParseException
            || e is MalformedJsonException
        ) {
            ex = ResponseThrowable(ERROR.PARSE_ERROR)
        } else if (e is ConnectException) {
            ex = ResponseThrowable(ERROR.NETWORD_ERROR)
        } else if (e is javax.net.ssl.SSLException) {
            ex = ResponseThrowable(ERROR.SSL_ERROR)
        } else if (e is ConnectTimeoutException
            || e is java.net.SocketTimeoutException
            || e is java.net.UnknownHostException
        ) {
            ex = ResponseThrowable(ERROR.TIMEOUT_ERROR)
        } else {
            ex = if (e.message.isNullOrEmpty()) ResponseThrowable(1000, e.message!!)
            else ResponseThrowable(ERROR.UNKNOWN)
        }
        return ex
    }
}