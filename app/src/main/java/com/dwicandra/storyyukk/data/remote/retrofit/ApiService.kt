package com.dwicandra.storyyukk.data.remote.retrofit

import com.dwicandra.storyyukk.data.remote.response.ResponseFileUpload
import com.dwicandra.storyyukk.data.remote.response.ResponseStory
import com.dwicandra.storyyukk.data.remote.response.auth.ResponseLogin
import com.dwicandra.storyyukk.data.remote.response.auth.ResponseRegister
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("/v1/register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<ResponseRegister>

    @FormUrlEncoded
    @POST("/v1/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<ResponseLogin>

    @Multipart
    @POST("/v1/stories")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<ResponseFileUpload>

    @GET("/v1/stories?location=")
    fun getStories(): Call<ResponseStory>
//
//    @GET("/v1/stories?location=1")
//    fun getLocationStories(): Call<ResponseStory>

}