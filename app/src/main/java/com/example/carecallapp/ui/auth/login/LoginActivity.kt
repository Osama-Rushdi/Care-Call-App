package com.example.carecallapp.ui.auth.login

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.carecallapp.R
import com.example.carecallapp.data.repository.view_models.AuthStateShow
import com.example.carecallapp.data.repository.view_models.MyAuthViewModel
import com.example.carecallapp.databinding.ActivityLoginBinding
import com.example.carecallapp.domain.model.auth.LoginRequest
import com.example.carecallapp.ui.hospital.HospitalActivity
import com.example.carecallapp.ui.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: MyAuthViewModel by viewModels()
    private lateinit var saveToken: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        saveToken = getSharedPreferences(Constants.SHARED_TOKEN_NAME, Context.MODE_PRIVATE)

        if (saveToken.getString(Constants.SHARED_TOKEN_KEY, null)!=null) {
            navigationToHomeScreen()
            finish()
        } else {
            observeLoginState()
            initListeners()
        }

    }

    private fun initListeners() {
        binding.emailLayout!!.editText!!.addTextChangedListener {
            binding.emailLayout!!.error = null
        }

        binding.passwordLayout.editText?.addTextChangedListener {
            binding.passwordLayout.error = null
        }

        binding.signInBtn!!.setOnClickListener {
            val email = binding.emailLayout!!.editText?.text.toString().trim()
            val password = binding.passwordLayout.editText?.text.toString().trim()
            isValidLogin(email, password)

            if (!isValidLogin(email, password)) return@setOnClickListener

            val loginRequest = LoginRequest(email = email, password = password)
            authViewModel.userLogIn(loginRequest)
        }
    }

    private fun observeLoginState() {
        authViewModel.stateShow.observe(this) { state ->
            when (state) {
                is AuthStateShow.Loading -> {
                    binding.loading!!.isVisible = true
                    binding.signInBtn!!.isEnabled = false
                }

                is AuthStateShow.IsSuccessLogin -> {
                    binding.loading!!.isVisible = false
                    binding.signInBtn!!.isEnabled = true
                    Toast.makeText(this, getString(R.string.login_successfully), Toast.LENGTH_SHORT)
                        .show()
                    saveToken.edit().putString(Constants.SHARED_TOKEN_KEY, state.userLogin.token)
                        .apply()
                    navigationToHomeScreen()
                }

                is AuthStateShow.ShowError -> {
                    binding.loading!!.isVisible = false
                    binding.signInBtn!!.isEnabled = true
                    showError(state.errorMessage)
                }

                else -> {}
            }
        }
    }

    private fun navigationToHomeScreen() {
        startActivity(Intent(this, HospitalActivity::class.java))
        finish()
    }

    private fun isValidLogin(email: String, password: String): Boolean {
        var isValid = true

        if (email.isEmpty()) {
            binding.emailLayout!!.error = "Email is required"
            isValid = false
        } else if (!email.contains("@") || !email.contains(".com")) {
            binding.emailLayout!!.error = "Email should be valid like example@mail.com"
            isValid = false
        }

        if (password.isEmpty()) {
            binding.passwordLayout.error = "Password is required"
            isValid = false
        } else if (password.length < 8) {
            binding.passwordLayout.error = "Password should be at least 8 characters"
            isValid = false
        }

        return isValid
    }

    private fun showError(message: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.error)).setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }.show()
    }

}