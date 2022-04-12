package com.dwicandra.storyyukk.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getDataUser(): Flow<UserModel> {
        return dataStore.data.map { preference ->
            UserModel(
                preference[NAME_KEY] ?: "",
                preference[EMAIL_KEY] ?: "",
                preference[KEY_TOKEN] ?: "",
                preference[KEY_LOGIN] ?: false,
            )
        }
    }

    suspend fun saveUser(user: UserModel) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[NAME_KEY] = user.name
            mutablePreferences[EMAIL_KEY] = user.email
            mutablePreferences[KEY_TOKEN] = user.token
            mutablePreferences[KEY_LOGIN] = user.isLogin
        }
    }

    suspend fun login() {
        dataStore.edit { preference ->
            preference[KEY_LOGIN] = true
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[KEY_LOGIN] = false
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val KEY_TOKEN = stringPreferencesKey("token")
        private val KEY_LOGIN = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}