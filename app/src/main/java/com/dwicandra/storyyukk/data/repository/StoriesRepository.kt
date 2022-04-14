package com.dwicandra.storyyukk.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dwicandra.storyyukk.data.remote.response.ListStoryItem
import com.dwicandra.storyyukk.data.remote.response.ResponseStory
import com.dwicandra.storyyukk.data.remote.retrofit.ApiService
import com.dwicandra.storyyukk.data.result.ResultState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                        println("awokaowkoawk. ${response.body()?.listStory}")
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