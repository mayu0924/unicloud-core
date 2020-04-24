package com.unicloud.core.demo.utils.sharedPreferences

import com.unicloud.core.demo.model.bean.ArticleBean

/**
 * @desc 文件存储服务
 *
 * @author yu.ma
 * @date 2020/4/24 12:04
 */
interface IAppData {
    fun saveArticle(article: ArticleBean)

    fun getArticle(): ArticleBean?
}