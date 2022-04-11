package com.dwicandra.storyyukk.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dwicandra.storyyukk.model.UserModel
import com.dwicandra.storyyukk.model.UserPreference
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: UserPreference): ViewModel() {
    fun getUser(): LiveData<UserModel>{
        return pref.getDataUser().asLiveData()
    }

    fun login(){
        viewModelScope.launch {
            pref.login()
        }
    }
}