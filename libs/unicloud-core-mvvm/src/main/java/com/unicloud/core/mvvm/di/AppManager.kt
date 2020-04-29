package com.unicloud.core.mvvm.di

import android.app.Application
import com.unicloud.core.mvvm.di.component.AppComponent
import com.unicloud.core.mvvm.di.component.DaggerAppComponent

/**
 * <pre>
 *     author : yu.ma
 *     e-mail : yu.ma@quanshi.com
 *     time   : 2020/4/29 15:52
 *     desc   : 依赖注入管理类
 *     version: 1.0
 * </pre>
 */
object AppManager {

    private lateinit var appComponent: AppComponent

    fun init(application: Application){
        appComponent = DaggerAppComponent.builder().application(application).build()
    }

    fun dbManager() = appComponent.dbManager()

}