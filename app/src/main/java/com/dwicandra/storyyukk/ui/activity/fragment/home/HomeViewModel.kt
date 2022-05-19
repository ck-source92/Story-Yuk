package com.dwicandra.storyyukk.ui.activity.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dwicandra.storyyukk.data.local.entity.StoriesEntity
import com.dwicandra.storyyukk.data.remote.response.ListStoryItem
import com.dwicandra.storyyukk.data.remote.response.ResponseStory
import com.dwicandra.storyyukk.data.repository.StoriesRepository
import com.dwicandra.storyyukk.data.result.ResultState

class HomeViewModel(private val storyRepository: StoriesRepository) : ViewModel() {
    val story: LiveData<PagingData<StoriesEntity>> =
        storyRepository.getAllStories().cachedIn(viewModelScope)
//
//    var getListStory: LiveData<ResultState<ResponseStory>> =
//        storyRepository.listStory
//
//    fun getAllStoryWithoutPaging(){
//        storyRepository.getAllStoryWithoutPaging()
//    }
}