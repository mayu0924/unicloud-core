package com.unicloud.core.mvvm.net.exception

enum class ERROR(private val code: Int, private val err: String) {

    /**
     * 未知错误
     */
    UNKNOWN(1000, "未知错误"),
    /**
     * 解析错误
     */
    PARSE_ERROR(1001, "解析错误"),
    /**
     * 网络错误
     */
    NETWORD_ERROR(1002, "连接失败"),
    /**
     * 协议出错
     */
    HTTP_ERROR(1003, "协议出错"),

    /**
     * 证书出错
     */
    SSL_ERROR(1004, "证书出错"),

    /**
     * 连接超时
     */
    TIMEOUT_ERROR(1006, "连接超时"),

    /**
     * TOKEN错误
     */
    TOKEN_ERROR(1007, "TOKEN错误"),

    /**
     * 重复请求
     */
    REPEAT_REQUEST(1008, "重复请求");

    fun getValue(): String {
        return err
    }

    fun getKey(): Int {
        return code
    }

}