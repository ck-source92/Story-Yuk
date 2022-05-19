package com.dwicandra.storyyukk.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dwicandra.storyyukk.data.remote.response.auth.ResponseRegister
import com.dwicandra.storyyukk.data.repository.AuthRepository
import com.dwicandra.storyyukk.data.result.ResultState

class SignupViewModel(private val authRepository: AuthRepository) : ViewModel() {
    var getRegister: LiveData<ResultState<ResponseRegister>>? =
        authRepository.getRegisterLiveData

    fun requestRegister(name: String, email: String, password: String) {
        authRepository.requestRegister(name, email, password)
    }
}