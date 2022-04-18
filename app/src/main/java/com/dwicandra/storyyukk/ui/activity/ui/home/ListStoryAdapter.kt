package com.dwicandra.storyyukk.ui.activity.ui.home

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dwicandra.storyyukk.R
import com.dwicandra.storyyukk.data.remote.response.ListStoryItem
import com.dwicandra.storyyukk.databinding.ItemStoryBinding
import com.dwicandra.storyyukk.ui.activity.DetailActivity
import androidx.core.util.Pair

class ListStoryAdapter(private val listStory: List<ListStoryItem>) :
    RecyclerView.Adapter<ListStoryAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(listStory: ListStoryItem) {
            Glide.with(itemView.context)
                .load(listStory.photoUrl)
                .error(R.mipmap.ic_launcher)
                .into(binding.storyImageView)
            binding.nameTextView.text = listStory.name
            binding.descTextView.text = listStory.description

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, listStory)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    override fun getItemCount(): Int = listStory.size

//    holder.binding.apply {
//        Glide.with(holder.itemView)
//            .load(listStory[position].photoUrl)
//            .error(R.mipmap.ic_launcher)
//            .into(storyImageView)
//        nameTextView.text = listStory[position].name
//        descTextView.text = listStory[position].description
//    }
//    holder.itemView.setOnClickListener{
//        val intent = Intent(holder.itemView.context, DetailActivity::class.java)
//        intent.putExtra(DetailActivity.EXTRA_ID, listStory[position])
//        val optionsCompat: ActivityOptionsCompat =
//            ActivityOptionsCompat.makeSceneTransitionAnimation(
//                holder.itemView as Activity,
//                Pair(, "profile"),
//                Pair(tvName, "name"),
//                Pair(tvDescription, "description"),
//            )
//        itemView.context.startActivity(intent, optionsCompat.toBundle())
//        holder.itemView.context.startActivity(intent)
//    }

}