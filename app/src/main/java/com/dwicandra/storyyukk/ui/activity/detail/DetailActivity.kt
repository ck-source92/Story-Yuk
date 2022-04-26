package com.dwicandra.storyyukk.ui.activity.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dwicandra.storyyukk.R
import com.dwicandra.storyyukk.data.remote.response.ListStoryItem
import com.dwicandra.storyyukk.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private var listStoryItem: ListStoryItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setDetailsUser()
    }

    private fun setDetailsUser() {
        listStoryItem = intent.getParcelableExtra(EXTRA_ID)
        listStoryItem?.let {
            binding.nameTextView.text = listStoryItem?.name
            binding.descTextView.text = listStoryItem?.description
            supportActionBar?.title = listStoryItem?.name
            Glide.with(this@DetailActivity)
                .load(listStoryItem?.photoUrl)
                .error(R.mipmap.ic_launcher)
                .into(binding.storyImageView)
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}