package com.dwicandra.storyyukk.ui.activity.ui.post

import androidx.lifecycle.ViewModel
import com.dwicandra.storyyukk.data.repository.StoriesRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PostViewModel(private val storyRepository: StoriesRepository) : ViewModel() {
    fun getListStories(){
        storyRepository.getAllStories()
    }
    fun uploadImage(file: MultipartBody.Part, description: RequestBody) {
        storyRepository.uploadImage(file, description)
    }
}