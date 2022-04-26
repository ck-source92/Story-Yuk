package com.dwicandra.storyyukk.ui.activity.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwicandra.storyyukk.databinding.FragmentHomeBinding
import com.dwicandra.storyyukk.ui.auth.ViewModelFactory
import com.dwicandra.storyyukk.ui.activity.main.HomeViewModelFactory
import com.dwicandra.storyyukk.ui.activity.ui.profile.ProfileViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val homeViewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory.getInstance(
            requireContext()
        )
    }
    private val profileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(
            requireContext()
        )
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupView()

        setupViewModel()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupView() {
        val layoutManager = LinearLayoutManager(activity)
        binding.rvStories.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvStories.addItemDecoration(itemDecoration)
    }

    private fun setupViewModel() {
        homeViewModel.getListStory.observe(viewLifecycleOwner) {
            if (it != null) {
                val adapter = ListStoryAdapter(it)
                binding.rvStories.adapter = adapter
            }
        }
        profileViewModel.getUser().observe(viewLifecycleOwner) {
            if (it.isLogin) {
                homeViewModel.getListStories()
            }
        }
    }
}