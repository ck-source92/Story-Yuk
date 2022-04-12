package com.dwicandra.storyyukk.ui.auth.signup

import androidx.lifecycle.ViewModel
import com.dwicandra.storyyukk.data.repository.AuthRepository

class SignupViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun requestRegister(name: String, email: String, password: String) {
        authRepository.requestRegister(name, email, password)
    }
}