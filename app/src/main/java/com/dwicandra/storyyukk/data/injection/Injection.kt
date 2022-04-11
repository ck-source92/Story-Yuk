package com.dwicandra.storyyukk.data.injection

import android.content.Context
import com.dwicandra.storyyukk.data.remote.retrofit.ApiConfig
import com.dwicandra.storyyukk.data.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }
}