package com.dwicandra.storyyukk.ui.auth.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dwicandra.storyyukk.R
import com.dwicandra.storyyukk.data.result.ResultState
import com.dwicandra.storyyukk.databinding.ActivitySignUpBinding
import com.dwicandra.storyyukk.ui.auth.AuthViewModelFactory
import com.dwicandra.storyyukk.ui.auth.login.LoginActivity
import com.google.android.material.snackbar.Snackbar

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    private var _binding: ActivitySignUpBinding? = null
    private val binding get() = _binding!!
    private val signupViewModel by viewModels<SignupViewModel> {
        AuthViewModelFactory.getInstance(
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAnimation()
        binding.btnSignUp.setOnClickListener(this)
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupViewModel() {
        signupViewModel.getRegister?.observe(this) {
            when (it) {
                is ResultState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ResultState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    showSnackBar(binding.root, "Success: ${it.data.message}")
                }
                is ResultState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showSnackBar(binding.root, "Error : ${it.error}")
                }
                else -> {}
            }
        }
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
        when {
            TextUtils.isEmpty(name) -> {
                binding.nameEdittext.error = "Field ini tidak boleh kosong"
            }
            TextUtils.isEmpty(email) -> {
                binding.emailEditText.error = "Field ini tidak boleh kosong"
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

    private fun setupAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -32f, 32f).apply {
            duration = 6000
        }.start()

        val signupBtn = ObjectAnimator.ofFloat(binding.btnSignUp, View.ALPHA, 1f).setDuration(1000)
        val loginBtn = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(1000)

        AnimatorSet().apply {
            playSequentially(signupBtn, loginBtn)
            start()
        }

    }

    private fun showSnackBar(view: View, message: String) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        val snackBarView = snackBar.view
        val txt =
            snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow_700))
        txt.setTextColor(ContextCompat.getColor(this, R.color.black))

        snackBar.show()
    }


}