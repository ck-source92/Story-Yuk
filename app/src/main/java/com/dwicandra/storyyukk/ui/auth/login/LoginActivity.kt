package com.dwicandra.storyyukk.ui.auth.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dwicandra.storyyukk.R
import com.dwicandra.storyyukk.data.result.ResultState
import com.dwicandra.storyyukk.databinding.ActivityLoginBinding
import com.dwicandra.storyyukk.ui.ViewModelFactory
import com.dwicandra.storyyukk.ui.activity.main.MainActivity
import com.dwicandra.storyyukk.ui.auth.signup.SignUpActivity
import com.google.android.material.snackbar.Snackbar


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel> { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        binding.btnLogin.setOnClickListener(this)
        binding.btnSignUp.setOnClickListener {

        }

        setupViewModel()
    }

    private fun setupViewModel() {
        loginViewModel.isLogin.observe(this) {
            when (it) {
                is ResultState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnLogin.isEnabled = false
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(binding.root.windowToken, 0)
                }
                is ResultState.Success -> {
                    showSnackBar(binding.root, "Berhasil Login")
                    binding.progressBar.visibility = View.GONE
                    loginViewModel.saveUser(it.data)
                    val intent = Intent(
                        this,
                        MainActivity::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
                is ResultState.Error -> {
                    showSnackBar(binding.root, "Error")
                    binding.progressBar.visibility = View.GONE
                    binding.btnLogin.isEnabled = true
                }
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
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        when (view?.id) {
            R.id.btnSignUp -> {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
            R.id.btnLogin -> {
                when {
                    TextUtils.isEmpty(email) -> {
                        binding.emailEditText.error = "Field ini tidak boleh kosong"
                    }
                    password.length <= 6 -> {
                        binding.passwordEditText.error = "Field ini tidak boleh kosong"
                    }
                    else -> {
                        loginViewModel.requestLogin(email, password)
                    }
                }
            }
        }
    }

    private fun showSnackBar(view: View, message: String?) {
        val snackBar = Snackbar.make(view, message!!, Snackbar.LENGTH_SHORT)
        val snackBarView = snackBar.view
        val txt =
            snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.teal_700))
        txt.setTextColor(ContextCompat.getColor(this, R.color.black))

        snackBar.show()
    }


}