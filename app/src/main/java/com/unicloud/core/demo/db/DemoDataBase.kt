package com.unicloud.core.demo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.unicloud.core.demo.db.dao.UserDao

/**
 * unicloud-core
 * ---------------------------------
 * created by mayu
 * on 2020/4/25
 */
@Database(entities = arrayOf(UserEntity::class), version = 1, exportSchema = false)
abstract class DemoDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: DemoDataBase? = null

        fun getDatabase(context: Context): DemoDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DemoDataBase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}