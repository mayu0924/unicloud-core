package com.unicloud.core.mvvm.event

/**
 *   @auther : Aleyn
 *   time   : 2019/11/13
 */
class Message @JvmOverloads constructor(
    var code: Int = 0,
    var msg: String = "",
    var obj: Any? = null
) {
    override fun toString(): String {
        return "Message(code=$code, msg='$msg', obj=$obj)"
    }
}