package com.unicloud.core.demo.model.repository

import com.unicloud.core.mvvm.net.BaseRepository
import com.unicloud.core.demo.net.AppResult
import com.unicloud.core.demo.model.bean.ArticleListBean
import com.unicloud.core.demo.net.RetrofitClient

class MainRepository : BaseRepository() {
    suspend fun getHomeArticles(page: Int): AppResult<ArticleListBean> = RetrofitClient.service.getHomeArticles(page)

}