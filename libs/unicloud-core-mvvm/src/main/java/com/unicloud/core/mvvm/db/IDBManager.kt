package com.unicloud.core.mvvm.db

import android.content.Context
import org.jetbrains.annotations.NotNull

/**
 * <pre>
 *     author : yu.ma
 *     e-mail : yu.ma@quanshi.com
 *     time   : 2020/4/29 16:18
 *     desc   : 数据库
 *     version: 1.0
 * </pre>
 */
interface IDBManager {
    fun init(@NotNull context: Context, @NotNull dbName: String)
}