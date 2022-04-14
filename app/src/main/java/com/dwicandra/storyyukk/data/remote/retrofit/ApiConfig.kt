package com.dwicandra.storyyukk.data.remote.retrofit

import com.dwicandra.storyyukk.BuildConfig
import com.dwicandra.storyyukk.model.UserPreference
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(
            userPreference: UserPreference
        ): ApiService {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor {
                    val original = it.request()
                    var token: String?
                    runBlocking {
                        token = userPreference.getToken()
                        if (!token.isNullOrEmpty()) {
                            val authorized = original.newBuilder()
                                .addHeader("Authorization", "Bearer $token")
                                .build()
                            it.proceed(authorized)
                        } else {
                            it.proceed(original)
                        }
                    }
                }
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}