package com.dwicandra.storyyukk.data.injection

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LifecycleOwner
import com.dwicandra.storyyukk.data.remote.retrofit.ApiConfig
import com.dwicandra.storyyukk.data.repository.AuthRepository
import com.dwicandra.storyyukk.data.repository.StoriesRepository
import com.dwicandra.storyyukk.model.UserPreference

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val preference = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(preference)
        return AuthRepository.getInstance(apiService, preference)
    }

    fun provideStoryRepository(context: Context): StoriesRepository{
        val preference = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(preference)
        return StoriesRepository.getInstance(apiService)
    }
}