package com.example.carecallapp.ui.auth.login

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.carecallapp.R
import com.example.carecallapp.data.repository.view_models.AuthStateShow
import com.example.carecallapp.data.repository.view_models.MyAuthViewModel
import com.example.carecallapp.databinding.FragmentLoginBinding
import com.example.carecallapp.domain.model.auth.LoginRequest
import com.example.carecallapp.ui.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: MyAuthViewModel by viewModels()
    private lateinit var saveToken: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveToken = requireActivity().getSharedPreferences(Constants.SHARED_TOKEN_NAME, Context.MODE_PRIVATE)

        if (saveToken.getString(Constants.SHARED_TOKEN_KEY, null) != null) {
            navigateToHomeScreen()
            return
        }

        setupObservers()
        initListeners()
    }

    private fun initListeners() {
        binding.emailLayout?.editText?.addTextChangedListener {
            binding.emailLayout?.error = null
        }

        binding.passwordLayout.editText?.addTextChangedListener {
            binding.passwordLayout.error = null
        }

        binding.signInBtn?.setOnClickListener {
            val email = binding.emailLayout?.editText?.text.toString().trim()
            val password = binding.passwordLayout.editText?.text.toString().trim()

            if (!isValidLogin(email, password)) return@setOnClickListener

            val loginRequest = LoginRequest(email = email, password = password)
            authViewModel.userLogIn(loginRequest)
        }
    }

    private fun setupObservers() {
        authViewModel.stateShow.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AuthStateShow.Loading -> {
                    binding.loading?.isVisible = true
                    binding.signInBtn?.isEnabled = false
                }

                is AuthStateShow.IsSuccessLogin -> {
                    binding.loading?.isVisible = false
                    binding.signInBtn?.isEnabled = true
                    Toast.makeText(requireContext(), getString(R.string.login_successfully), Toast.LENGTH_SHORT).show()
                    saveToken.edit().putString(Constants.SHARED_TOKEN_KEY, state.userLogin.token).apply()
                    navigateToHomeScreen()
                }

                is AuthStateShow.ShowError -> {
                    binding.loading?.isVisible = false
                    binding.signInBtn?.isEnabled = true
                    showError(state.errorMessage)
                }

                else -> {}
            }
        }
    }

    private fun navigateToHomeScreen() {
        findNavController().navigate(R.id.homeFragment)
    }

    private fun isValidLogin(email: String, password: String): Boolean {
        var isValid = true

        if (email.isEmpty()) {
            binding.emailLayout?.error = "Email is required"
            isValid = false
        } else if (!email.contains("@") || !email.contains(".com")) {
            binding.emailLayout?.error = "Email should be valid like example@mail.com"
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
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.error))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}