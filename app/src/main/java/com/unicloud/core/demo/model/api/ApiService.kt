package com.unicloud.core.demo.model.api

import com.unicloud.core.demo.net.AppResult
import com.unicloud.core.demo.model.bean.ArticleListBean
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ApiService {
    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): AppResult<ArticleListBean>

    @Streaming
    @GET
    suspend fun downloadFile(@Url url: String): ResponseBody
}