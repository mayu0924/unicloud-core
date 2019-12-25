package com.unicloud.core.mvvm.net

//abstract class BaseResult<T>(var code: Int, var message: String, var result: T) {
//    abstract fun isSuccess(): Boolean
//}
interface BaseResult<T> {

    fun isSuccess(): Boolean

    fun code():Int

    fun message(): String

    fun data():T?
}
