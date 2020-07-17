package com.unicloud.core.mvvm.di.component

import android.app.Application
import androidx.room.RoomDatabase
import com.unicloud.core.mvvm.db.DBManager
import com.unicloud.core.mvvm.db.IDBManager
import com.unicloud.core.mvvm.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import org.jetbrains.annotations.NotNull
import javax.inject.Singleton

/**
 * <pre>
 *     author : yu.ma
 *     e-mail : yu.ma@quanshi.com
 *     time   : 2020/4/29 15:55
 *     desc   : 依赖管理
 *     version: 1.0
 * </pre>
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun application(): Application

    fun dbManager(): DBManager

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(@NotNull application: Application): Builder

        fun build(): AppComponent
    }
}