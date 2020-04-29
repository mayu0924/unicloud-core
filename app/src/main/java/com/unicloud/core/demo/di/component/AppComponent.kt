package com.unicloud.core.demo.di.component

import android.app.Application
import com.unicloud.core.demo.db.AppDataBase
import com.unicloud.core.demo.utils.sharedPreferences.AppData
import com.unicloud.core.demo.utils.sharedPreferences.IAppData
import dagger.BindsInstance
import dagger.Component
import javax.inject.Inject
import javax.inject.Singleton

/**
 * <pre>
 *     author : yu.ma
 *     e-mail : yu.ma@quanshi.com
 *     time   : 2020/4/28 16:31
 *     desc   : AppComponent
 *     version: 1.0
 * </pre>
 */
//@Singleton
//@Component(modules = [AppModule::class])
//interface AppComponent {
//    fun application(): Application
//
//    fun appData(): AppData
//
//    fun appDatabase(): AppDataBase
//
//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        fun application(application: Application): Builder
//
//        fun build(): AppComponent
//    }
//}