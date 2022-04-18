package com.dwicandra.storyyukk.ui.activity.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dwicandra.storyyukk.data.injection.Injection
import com.dwicandra.storyyukk.data.repository.StoriesRepository
import com.dwicandra.storyyukk.ui.activity.ui.home.HomeViewModel
import com.dwicandra.storyyukk.ui.activity.ui.post.PostViewModel

class HomeViewModelFactory(private val storyRepository: StoriesRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(PostViewModel::class.java) -> {
                PostViewModel(storyRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: HomeViewModelFactory? = null
        fun getInstance(context: Context): HomeViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: HomeViewModelFactory(
                    Injection.provideStoryRepository(
                        context
                    )
                )
            }.also { instance = it }
    }
}