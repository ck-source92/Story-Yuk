package com.dwicandra.storyyukk.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwicandra.storyyukk.data.repository.AuthRepository
import com.dwicandra.storyyukk.data.result.ResultState
import com.dwicandra.storyyukk.model.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _login = MutableLiveData<ResultState<UserModel>>()
    val isLogin: LiveData<ResultState<UserModel>> = _login

    fun requestLogin(email: String, password: String) {
        authRepository.requestLogin(email, password, _login)
    }

    fun saveUser(userModel: UserModel) {
        viewModelScope.launch {
            authRepository.getUserPref().saveUser(
                userModel
            )
        }
    }
}