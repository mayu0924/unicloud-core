package com.unicloud.core.demo.utils.sharedPreferences

import androidx.core.content.edit
import com.google.gson.reflect.TypeToken
import com.unicloud.core.demo.model.bean.ArticleBean
import com.unicloud.core.mvvm.data.SharedPreferencesHelper
import com.unicloud.core.mvvm.data.SharedPreferencesHelper.getObject
import com.unicloud.core.mvvm.data.SharedPreferencesHelper.putObject

/**
 * @desc 数据存储
 *
 * @author yu.ma
 * @date 2020/4/24 12:05
 */
class AppData : IAppData {
    override fun saveArticle(article: ArticleBean) {
        SharedPreferencesHelper.sp.edit { putObject("article", article) }
    }

    override fun getArticle(): ArticleBean? {
        return SharedPreferencesHelper.sp.getObject<ArticleBean>(
            "article",
            object : TypeToken<ArticleBean>() {}.type,
            null
        )
    }
}