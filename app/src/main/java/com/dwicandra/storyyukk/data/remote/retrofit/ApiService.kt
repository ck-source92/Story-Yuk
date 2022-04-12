package com.dwicandra.storyyukk.data.remote.retrofit

import com.dwicandra.storyyukk.data.remote.response.auth.ResponseLogin
import com.dwicandra.storyyukk.data.remote.response.auth.ResponseRegister
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("/v1/register")
    fun register(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("password") password:String,
    ): Call<ResponseRegister>

    @FormUrlEncoded
    @POST("/v1/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ):Call<ResponseLogin>
}