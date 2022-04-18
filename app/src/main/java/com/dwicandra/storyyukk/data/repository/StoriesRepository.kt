package com.dwicandra.storyyukk.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dwicandra.storyyukk.data.remote.response.ListStoryItem
import com.dwicandra.storyyukk.data.remote.response.ResponseFileUpload
import com.dwicandra.storyyukk.data.remote.response.ResponseStory
import com.dwicandra.storyyukk.data.remote.retrofit.ApiService
import com.dwicandra.storyyukk.data.result.ResultState
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class StoriesRepository private constructor(private val apiService: ApiService) {

    private val _listStory = MutableLiveData<List<ListStoryItem>>()
    val listStory: LiveData<List<ListStoryItem>> = _listStory

    fun getAllStories() {
        val client = apiService.getStories()
        client.enqueue(object : Callback<ResponseStory> {
            override fun onResponse(call: Call<ResponseStory>, response: Response<ResponseStory>) {
                if (response.isSuccessful) {
                    if (response.body()?.error == false) {
                        response.message()
                        _listStory.value = response.body()?.listStory
                    } else {
                        ResultState.Loading
                    }
                }
            }

            override fun onFailure(call: Call<ResponseStory>, t: Throwable) {
                ResultState.Error("ERROR")
            }
        })
    }

    fun uploadImage(photo: MultipartBody.Part, description: RequestBody) {
        val client = apiService.uploadImage(photo, description)
        client.enqueue(object : Callback<ResponseFileUpload>{
            override fun onResponse(
                call: Call<ResponseFileUpload>,
                response: Response<ResponseFileUpload>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error){
                        response.message()
                    }else{
                        ResultState.Loading
                    }
                }
            }

            override fun onFailure(call: Call<ResponseFileUpload>, t: Throwable) {
                ResultState.Error("ERROR UPLOAD IMAGE")
            }
        })

    }

    companion object {
        @Volatile
        private var instance: StoriesRepository? = null
        fun getInstance(
            apiService: ApiService
        ): StoriesRepository =
            instance ?: synchronized(this) {
                instance ?: StoriesRepository(apiService)
            }.also { instance = it }
    }
}