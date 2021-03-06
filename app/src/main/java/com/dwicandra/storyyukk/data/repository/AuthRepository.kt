package com.dwicandra.storyyukk.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dwicandra.storyyukk.data.remote.response.auth.ResponseLogin
import com.dwicandra.storyyukk.data.remote.response.auth.ResponseRegister
import com.dwicandra.storyyukk.data.remote.retrofit.ApiService
import com.dwicandra.storyyukk.data.result.ResultState
import com.dwicandra.storyyukk.model.UserModel
import com.dwicandra.storyyukk.model.UserPreference
import com.dwicandra.storyyukk.util.ErrorParse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
) {
    private val _register = MutableLiveData<ResultState<ResponseRegister>>()
    val getRegisterLiveData: LiveData<ResultState<ResponseRegister>> = _register

    fun getUserPref(): UserPreference {
        return userPreference
    }

    fun requestRegister(
        name: String,
        email: String,
        password: String,
    ) {
        val client = apiService.register(name, email, password)
        _register.value = ResultState.Loading
        client.enqueue(object : Callback<ResponseRegister> {
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.error == false) {
                        _register.postValue(
                            ResultState.Success(
                                ResponseRegister(
                                    response.body()!!.error,
                                    response.body()!!.message
                                )
                            )
                        )
                        response.body()?.message
                    } else {
                        _register.postValue(
                            ResultState.Error(
                                error = ErrorParse.parse(response)?.message ?: "error "
                            )
                        )
                    }
                } else {
                    _register.postValue(
                        ResultState.Error(
                            error = ErrorParse.parse(response)?.message ?: "error "
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                _register.postValue(
                    ResultState.Error(
                        "Gagal"
                    )
                )
            }
        })
    }

    fun requestLogin(
        email: String,
        password: String,
        mutableLiveData: MutableLiveData<ResultState<UserModel>>
    ) {
        val client = apiService.login(email, password)
        mutableLiveData.value = ResultState.Loading
        client.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(
                call: Call<ResponseLogin>,
                response: Response<ResponseLogin>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.error == false) {
                        val loginResult = response.body()?.loginResultResult
                        mutableLiveData.postValue(
                            ResultState.Success(
                                UserModel(
                                    loginResult?.name ?: "",
                                    email,
                                    loginResult?.token ?: "",
                                    true
                                )
                            )
                        )
                        response.body()?.message
                    } else {
                        mutableLiveData.postValue(ResultState.Error("Gagal"))
                        response.body()?.message
                    }
                } else {
                    mutableLiveData.postValue(
                        ResultState.Error(
                            error = ErrorParse.parse(response)?.message ?: "error "
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                mutableLiveData.postValue(ResultState.Error("Gagal"))
            }
        })
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService, userPreference)
            }.also { instance = it }
    }
}