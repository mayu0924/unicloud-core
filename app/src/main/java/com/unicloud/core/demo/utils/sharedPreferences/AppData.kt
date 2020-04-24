package com.unicloud.core.demo.utils.sharedPreferences

import androidx.core.content.edit
import com.google.gson.reflect.TypeToken
import com.unicloud.core.demo.model.bean.ArticleBean

/**
 * @desc 数据存储
 *
 * @author yu.ma
 * @date 2020/4/24 12:05
 */
object AppData : SharedPreferencesHelper(), LocalDataService {
    override fun saveArticle(article: ArticleBean) {
        sp.edit { putObject<ArticleBean>("article", article) }
    }

    override fun getArticle(): ArticleBean? {
        return sp.getObject<ArticleBean>("article", object : TypeToken<ArticleBean>() {}.type, null)
    }
}