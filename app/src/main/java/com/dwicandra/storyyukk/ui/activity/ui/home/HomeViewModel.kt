package com.dwicandra.storyyukk.ui.activity.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dwicandra.storyyukk.data.remote.response.ListStoryItem
import com.dwicandra.storyyukk.data.repository.StoriesRepository

class HomeViewModel(private val storyRepository: StoriesRepository) : ViewModel() {
    var getListStory: LiveData<List<ListStoryItem>> =
        storyRepository.listStory

    fun getListStories(){
        storyRepository.getAllStories()
    }
}