package com.dwicandra.storyyukk.ui.auth.signup

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dwicandra.storyyukk.R
import com.dwicandra.storyyukk.databinding.ActivitySignUpBinding
import com.dwicandra.storyyukk.ui.ViewModelFactory

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySignUpBinding

    private val signupViewModel by viewModels<SignupViewModel> { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        binding.btnSignUp.setOnClickListener(this)
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    override fun onClick(view: View?) {
        val name = binding.nameEdittext.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        when{
            TextUtils.isEmpty(name) -> {
                binding.nameEdittext.error = "Field ini tidak boleh kosong"
            }
            TextUtils.isEmpty(email) -> {
                binding.emailEditText.error = "Field ini tidak boleh kosong"
            }
            password.length <= 6 -> {
                binding.nameEdittext.error = "Password harus lebih dari 6"
            }
            else -> {
                when (view?.id) {
                    R.id.btnSignUp -> {
                        signupViewModel.requestRegister(name, email, password)
                    }
                }
            }
        }
    }

}