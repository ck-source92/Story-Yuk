package com.dwicandra.storyyukk.ui.auth.signup

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dwicandra.storyyukk.databinding.ActivitySignUpBinding
import com.dwicandra.storyyukk.ui.ViewModelFactory
import com.dwicandra.storyyukk.ui.auth.login.LoginActivity

//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
//    private lateinit var signupViewModel: SignupViewModel

    private val signupViewModel by viewModels<SignupViewModel> { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
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

//    private fun setupViewModel() {
//        signupViewModel = ViewModelProvider(
//            this,
//            ViewModelFactory(UserRepository.getInstance())
//        )[SignupViewModel::class.java]
//    }

//    private fun setupAction() {
//        binding.btnSignUp.setOnClickListener {
//            val name = binding.nameEdittext.text.toString()
//            val email = binding.emailEditText.text.toString()
//            val password = binding.passwordEditText.text.toString()
//            when {
//                name.isEmpty() -> {
//                    binding.nameEditTextLayout.error = "Masukkan name"
//                }
//                email.isEmpty() -> {
//                    binding.emailEditTextLayout.error = "Masukkan email"
//                }
//                password.isEmpty() -> {
//                    binding.passwordEditTextLayout.error = "Masukkan password"
//                }
//                else -> {
//                    signupViewModel.saveUser(UserModel(name, email, password, false))
//                    AlertDialog.Builder(this).apply {
//                        setTitle("Yeah!")
//                        setMessage("Akunnya $name $email sudah jadi nih. Yuk, login dan belajar coding.")
//                        setPositiveButton("Lanjut") { _, _ ->
//                            val intent = Intent(context, LoginActivity::class.java)
//                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                            startActivity(intent)
//                            finish()
//                        }
//                        create()
//                        show()
//                    }
//                }
//            }
//        }
//    }
}