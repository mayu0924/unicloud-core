package com.unicloud.core.demo.app

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import com.blankj.utilcode.util.Utils
import com.facebook.stetho.Stetho
import com.unicloud.core.demo.di.AppManager
import com.unicloud.core.demo.net.RetrofitClient
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits

class MApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)

        AppManager.init(this)

        RetrofitClient.init(this)

        Stetho.initializeWithDefaults(this)

        AutoSizeConfig.getInstance().unitsManager
            .setSupportDP(true).supportSubunits = Subunits.NONE
        AutoSizeConfig.getInstance().isCustomFragment = true
    }
}