package com.unicloud.core.mvvm.net.upload

interface ProgressListener {
    fun onProgress(
        hasWrittenLen: Long,
        totalLen: Long,
        hasFinish: Boolean
    )
}