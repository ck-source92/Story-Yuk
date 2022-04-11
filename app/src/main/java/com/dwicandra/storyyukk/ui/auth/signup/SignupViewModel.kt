package com.dwicandra.storyyukk.ui.auth.signup

import androidx.lifecycle.ViewModel
import com.dwicandra.storyyukk.data.repository.UserRepository

class SignupViewModel(private val userRepository: UserRepository) : ViewModel() {
//    fun saveUser(user: UserModel){
//        viewModelScope.launch {
//            pref.saveUser(user)
//        }
//    }

    fun requestRegister(name: String, email: String, password: String) {
        userRepository.requestRegister(name, email, password)
    }
}