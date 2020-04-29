package com.unicloud.core.demo.di.module

import android.content.Context
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.Utils
import com.unicloud.core.demo.db.AppDataBase
import com.unicloud.core.demo.utils.sharedPreferences.AppData
import com.unicloud.core.demo.utils.sharedPreferences.IAppData
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * <pre>
 *     author : yu.ma
 *     e-mail : yu.ma@quanshi.com
 *     time   : 2020/4/28 16:32
 *     desc   : CacheModule
 *     version: 1.0
 * </pre>
 */
//@Module
//class AppModule {
//
//    @Provides
//    @Singleton
//    fun providerAppDatabase(): AppDataBase {
//        return Room.databaseBuilder(
//            Utils.getApp(),
//            AppDataBase::class.java,
//            AppUtils.getAppName()
//        ).build()
//    }
//}