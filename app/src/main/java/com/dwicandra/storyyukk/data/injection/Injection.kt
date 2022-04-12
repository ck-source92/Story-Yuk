package com.dwicandra.storyyukk.data.injection

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dwicandra.storyyukk.data.remote.retrofit.ApiConfig
import com.dwicandra.storyyukk.data.repository.AuthRepository
import com.dwicandra.storyyukk.model.UserPreference

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val preference = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(preference)
        return AuthRepository.getInstance(apiService, preference)
    }
}