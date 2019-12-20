package com.unicloud.core.demo.net

import com.unicloud.core.mvvm.BaseResult

data class AppResult<T>(var errorCode: Int, var errorMsg: String, var data: T) :
    BaseResult<T> {

    override fun code(): Int = errorCode

    override fun message(): String = errorMsg

    override fun data(): T = data

    override fun isSuccess(): Boolean = errorCode == 0
}