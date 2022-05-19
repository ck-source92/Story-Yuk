package com.dwicandra.storyyukk.ui.activity.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dwicandra.storyyukk.data.remote.response.ListStoryItem
import com.dwicandra.storyyukk.data.repository.StoriesRepository
import com.dwicandra.storyyukk.data.result.ResultState

class MapsViewModel(private val storyRepository: StoriesRepository): ViewModel() {
    var allStoriesLocation: LiveData<ResultState<List<ListStoryItem>>> =
        storyRepository.allStoriesLocation

    fun getAllStoriesLocation(){
        storyRepository.getAllStoriesLocation()
    }
}