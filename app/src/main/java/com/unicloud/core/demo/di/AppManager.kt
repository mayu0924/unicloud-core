package com.unicloud.core.demo.di

import android.app.Application
import com.unicloud.core.demo.db.AppDataBase
import com.unicloud.core.demo.di.component.AppComponent
import com.unicloud.core.demo.di.component.DaggerAppComponent
import com.unicloud.core.demo.utils.sharedPreferences.IAppData

/**
 * <pre>
 *     author : yu.ma
 *     e-mail : yu.ma@quanshi.com
 *     time   : 2020/4/28 16:55
 *     desc   : AppManager
 *     version: 1.0
 * </pre>
 */
object AppManager {
    private lateinit var appComponent: AppComponent
    fun init(application: Application) {
        appComponent =
            DaggerAppComponent.builder().application(application)
                .build() //如果提示找不到DaggerRingComponent类，请重新编译下项目。
    }

    fun dataManager(): IAppData = requireNotNull(appComponent.provideAppData()) { "请在AppModule中设置数据管理类" }

    fun dbManager(): AppDataBase = requireNotNull(appComponent.providerAppDatabase()) { "请在AppModule中设置数据库管理类" }
}