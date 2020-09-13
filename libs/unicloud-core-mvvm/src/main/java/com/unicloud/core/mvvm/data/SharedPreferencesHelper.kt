package com.unicloud.core.mvvm.data

import android.content.Context
import android.content.SharedPreferences
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.Utils
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * @desc 文件存储
 *
 * @author yu.ma
 * @date 2020/4/24 11:54
 */
object SharedPreferencesHelper {
    var sp: SharedPreferences =
        Utils.getApp().getSharedPreferences(AppUtils.getAppPackageName(), Context.MODE_PRIVATE)

    fun <T> SharedPreferences.Editor.putObject(key: String, value: T?) {
        if (value == null) {
            remove(key)
            return
        }
        val json = GsonUtils.toJson(value, object : TypeToken<T>() {}.type)
        putString(key, json)
    }

    fun <T> SharedPreferences.getObject(key: String, type: Type, defaultValue: T?): T? {
        val str = getString(key, "")
        return if (str.isNullOrEmpty()) defaultValue
        else GsonUtils.fromJson<T>(str, type)
    }
}