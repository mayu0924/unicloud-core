package com.unicloud.core.mvvm.net

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @author mayu
 * @date 2020/2/11
 */
interface BaseService {
    //改操作用于下载文件,url传入下载的全路径,Streaming在大文件下载时必须添加,ResponseBody封装下载的流
    @Streaming
    @GET
    fun downloadFile(@Url url: String): Call<ResponseBody>

    @Multipart
    @POST
    fun uploadFile(@Url url: String, @Part file: MultipartBody.Part?): Call<ResponseBody>

    @Multipart
    @POST
    fun uploadMultiFile(@Url url: String, @PartMap files: MutableMap<String, RequestBody>): Call<ResponseBody>
}