package com.dwicandra.storyyukk.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dwicandra.storyyukk.R
import com.dwicandra.storyyukk.data.local.entity.StoriesEntity
import com.dwicandra.storyyukk.databinding.ItemStoryBinding
import com.dwicandra.storyyukk.ui.activity.detail.DetailActivity

class StoryPagingAdapter :
    PagingDataAdapter<StoriesEntity, StoryPagingAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class ViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: StoriesEntity) {
            Glide.with(itemView.context)
                .load(story.photoUrl)
                .error(R.mipmap.ic_launcher)
                .into(binding.storyImageView)
            binding.nameTextView.text = story.name
            binding.descTextView.text = story.description

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, story)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.storyImageView, "profile"),
                        Pair(binding.nameTextView, "name"),
                        Pair(binding.descTextView, "description"),
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoriesEntity>() {
            override fun areItemsTheSame(
                oldItem: StoriesEntity,
                newItem: StoriesEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: StoriesEntity,
                newItem: StoriesEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}