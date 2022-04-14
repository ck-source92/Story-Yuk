package com.dwicandra.storyyukk.ui

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dwicandra.storyyukk.data.injection.Injection
import com.dwicandra.storyyukk.data.remote.response.ResponseStory
import com.dwicandra.storyyukk.data.repository.AuthRepository
import com.dwicandra.storyyukk.ui.activity.ui.home.HomeViewModel
import com.dwicandra.storyyukk.ui.activity.ui.profile.ProfileViewModel
import com.dwicandra.storyyukk.ui.auth.login.LoginViewModel
import com.dwicandra.storyyukk.ui.auth.signup.SignupViewModel

class ViewModelFactory(private val authRepository: AuthRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(authRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideAuthRepository(context))
            }.also { instance = it }
    }
}