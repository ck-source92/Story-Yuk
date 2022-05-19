package com.dwicandra.storyyukk.ui.activity.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dwicandra.storyyukk.databinding.FragmentHomeBinding
import com.dwicandra.storyyukk.ui.HomeViewModelFactory
import com.dwicandra.storyyukk.ui.adapter.LoadingStateAdapter
import com.dwicandra.storyyukk.ui.adapter.StoryPagingAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory.getInstance(
            requireContext()
        )
    }

    private lateinit var storyPagingAdapter: StoryPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        storyPagingAdapter = StoryPagingAdapter()
        binding.swipeRefresh.setOnRefreshListener {
            getDataPaging()
        }

        getDataPaging()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getDataPaging()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getDataPaging() {
        val layoutManager = LinearLayoutManager(activity)
        binding.rvStories.layoutManager = layoutManager

        storyPagingAdapter.refresh()
        binding.rvStories.adapter = storyPagingAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                storyPagingAdapter.retry()
            }
        )
        storyPagingAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    layoutManager.scrollToPosition(0)
                }
            }
        })
        homeViewModel.story.observe(viewLifecycleOwner) {
            storyPagingAdapter.submitData(lifecycle, it)
        }
        binding.swipeRefresh.isRefreshing = false
    }

}