package com.unicloud.core.demo.app

import android.app.Application
import com.unicloud.core.demo.net.RetrofitClient

class MApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        RetrofitClient.init(this)
        RetrofitClient.client
    }
}