package com.unicloud.core.mvvm

import android.app.Application
import com.blankj.utilcode.util.Utils
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits

/**
 * 继承此Application
 */
open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AutoSizeConfig.getInstance().unitsManager
            .setSupportDP(true).supportSubunits = Subunits.NONE
        AutoSizeConfig.getInstance().isCustomFragment = true

        Utils.init(this)
    }
}