package com.unicloud.core.mvvm.data

import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

object DataStoreHelper {

    private val dataStore: DataStore<Preferences> by lazy {
        Utils.getApp().createDataStore(
            name = AppUtils.getAppPackageName(),
            migrations = listOf(
                SharedPreferencesMigration(
                    Utils.getApp(),
                    AppUtils.getAppPackageName()
                )
            )
        )
    }

    /**
     * @param key Key的类型只能是Int, Long, Boolean, Float, String, Set
     */
    fun <T> save(
        scope: CoroutineScope,
        key: Preferences.Key<T>,
        value: T?,
        callback: () -> Unit
    ) = scope.launch {
        dataStore.edit {
            if (null == value) {
                it.remove(key)
            } else {
                it[key] = value
            }
        }
        with(Dispatchers.Main) {
            callback.invoke()
        }
    }

    /**
     * 读取数据
     * @return
     */
    fun <T> getValue(
        scope: CoroutineScope,
        key: Preferences.Key<T>,
        defaultValue: T?,
        callback: (t: T?) -> Unit
    ) = scope.launch {
        dataStore.data.map {
            it[key] ?: defaultValue
        }.collect {
            with(Dispatchers.Main) {
                callback.invoke(it)
            }
        }
    }
}