package com.dwicandra.storyyukk.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.dwicandra.storyyukk.data.local.entity.StoriesEntity
import com.dwicandra.storyyukk.data.local.room.StoriesDatabase
import com.dwicandra.storyyukk.data.paging.StoriesRemoteMediator
import com.dwicandra.storyyukk.data.remote.response.ListStoryItem
import com.dwicandra.storyyukk.data.remote.response.ResponseStory
import com.dwicandra.storyyukk.data.remote.retrofit.ApiService
import com.dwicandra.storyyukk.data.result.ResultState
import com.dwicandra.storyyukk.util.ErrorParse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoriesRepository(
    private val storiesDatabase: StoriesDatabase,
    private val apiService: ApiService
) {
    private val _allStoriesLocation = MutableLiveData<ResultState<List<ListStoryItem>>>()
    val allStoriesLocation: LiveData<ResultState<List<ListStoryItem>>> = _allStoriesLocation

    fun getAllStories(): LiveData<PagingData<StoriesEntity>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoriesRemoteMediator(
                database = storiesDatabase,
                apiService = apiService
            ),
            pagingSourceFactory = {
                storiesDatabase.storiesDao().getAllStories()
            }
        ).liveData
    }

    fun uploadImage(
        photo: MultipartBody.Part, description: RequestBody
    ) {
        val client = apiService.uploadImage(photo, description)
        client.enqueue(object : Callback<ResponseStory> {
            override fun onResponse(
                call: Call<ResponseStory>,
                response: Response<ResponseStory>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        response.message()
                    }
                } else {
                    response.body()?.message
                }
            }

            override fun onFailure(call: Call<ResponseStory>, t: Throwable) {
                ResultState.Error("ERROR UPLOAD IMAGE")
            }
        })

    }

    fun getAllStoriesLocation() {
        val client = apiService.getLocationStories()
        client.enqueue(object : Callback<ResponseStory> {
            override fun onResponse(
                call: Call<ResponseStory>,
                response: Response<ResponseStory>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.error == false) {
                        response.message()
                        _allStoriesLocation.postValue(ResultState.Success(response.body()!!.listStory))
                    } else {
                        _allStoriesLocation.postValue(ResultState.Loading)
                    }
                } else {
                    ResultState.Error(
                        ErrorParse.parse(response)?.message ?: "error"
                    )
                }
            }

            override fun onFailure(call: Call<ResponseStory>, t: Throwable) {
                _allStoriesLocation.postValue(
                    ResultState.Error(
                        ErrorParse.parse(call.execute())?.message ?: "error"
                    )
                )
            }
        })
    }

    companion object {
        @Volatile
        private var instance: StoriesRepository? = null
        fun getInstance(
            apiService: ApiService,
            database: StoriesDatabase
        ): StoriesRepository =
            instance ?: synchronized(this) {
                instance ?: StoriesRepository(database, apiService)
            }.also { instance = it }
    }
}
