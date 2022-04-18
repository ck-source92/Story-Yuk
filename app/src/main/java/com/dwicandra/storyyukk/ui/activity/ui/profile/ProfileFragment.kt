package com.dwicandra.storyyukk.ui.activity.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dwicandra.storyyukk.databinding.FragmentProfileBinding
import com.dwicandra.storyyukk.ui.auth.ViewModelFactory
import com.dwicandra.storyyukk.ui.auth.login.LoginActivity

class ProfileFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentProfileBinding? = null
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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        setupViewModel()
        binding.logoutButton.setOnClickListener(this)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViewModel() {
        profileViewModel.getUser().observe(viewLifecycleOwner) {
            if (!it.isLogin) {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onClick(view: View?) {
        profileViewModel.logout()
    }
}