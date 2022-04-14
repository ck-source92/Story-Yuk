package com.dwicandra.storyyukk.ui.activity.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dwicandra.storyyukk.data.repository.AuthRepository
import com.dwicandra.storyyukk.model.UserModel
import kotlinx.coroutines.launch

class ProfileViewModel(private val authRepository: AuthRepository) : ViewModel() {
    /**
    private val _text = MutableLiveData<String>().apply {
    value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
     **/
    fun getUser(): LiveData<UserModel> {
        return authRepository.getUserPref().getDataUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.getUserPref().logout()
        }
    }
}