package com.dwicandra.storyyukk.data.injection

import android.content.Context
import com.dwicandra.storyyukk.data.remote.retrofit.ApiConfig
import com.dwicandra.storyyukk.data.repository.AuthRepository

object Injection {
    fun provideRepository(context: Context): AuthRepository {
        val apiService = ApiConfig.getApiService()
        return AuthRepository.getInstance(apiService)
    }
}