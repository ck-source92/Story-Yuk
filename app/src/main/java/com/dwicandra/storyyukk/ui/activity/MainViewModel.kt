package com.dwicandra.storyyukk.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dwicandra.storyyukk.data.repository.AuthRepository
import com.dwicandra.storyyukk.model.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val authRepository: AuthRepository): ViewModel(){
    fun getUser(): LiveData<UserModel> {
        return authRepository.getUserPref().getDataUser().asLiveData()
    }

    fun logout(){
        viewModelScope.launch {
            authRepository.getUserPref().logout()
        }
    }
}