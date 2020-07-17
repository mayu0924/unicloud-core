package com.unicloud.core.mvvm.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import javax.inject.Inject
import javax.inject.Singleton

/**
 * <pre>
 *     author : yu.ma
 *     e-mail : yu.ma@quanshi.com
 *     time   : 2020/4/29 16:23
 *     desc   : Room数据库
 *     version: 1.0
 * </pre>
 */
@Singleton
class DBManager @Inject constructor() : IDBManager {

    override fun init(context: Context, dbName: String) {

    }
}