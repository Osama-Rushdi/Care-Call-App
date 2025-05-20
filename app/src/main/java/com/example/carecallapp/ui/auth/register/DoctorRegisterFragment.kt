package com.example.carecallapp.ui.auth.register

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
import com.example.carecallapp.databinding.FragmentDoctorRegisterBinding
import com.example.carecallapp.domain.model.auth.DoctorRegisterRequest
import com.example.carecallapp.ui.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorRegisterFragment : Fragment() {
    private var _binding: FragmentDoctorRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: MyAuthViewModel by viewModels()
    private lateinit var saveToken: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveToken = requireActivity().getSharedPreferences(
            Constants.SHARED_TOKEN_NAME,
            Context.MODE_PRIVATE
        )

        if (saveToken.getString(Constants.SHARED_TOKEN_KEY, null) != null) {
            navigateToHomeScreen()
            return
        }

        setupObservers()
        initListeners()
        setupTextWatchers()
    }

    private fun setupTextWatchers() {
        binding.usernameLayout.editText?.addTextChangedListener {
            binding.usernameLayout.error = null
        }
        binding.emailLayout.editText?.addTextChangedListener {
            binding.emailLayout.error = null
        }
        binding.passwordLayout.editText?.addTextChangedListener {
            binding.passwordLayout.error = null
        }
        binding.confirmPasswordLayout.editText?.addTextChangedListener {
            binding.confirmPasswordLayout.error = null
        }
        // Add watchers for other fields as needed
    }

    private fun initListeners() {
        binding.signUpBtn.setOnClickListener {
            if (validateInputs()) {
                registerDoctor()
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        // Username validation
        if (binding.usernameLayout.editText?.text.isNullOrEmpty()) {
            binding.usernameLayout.error = getString(R.string.username_is_required)
            isValid = false
        }

        // Email validation
        val email = binding.emailLayout.editText?.text.toString().trim()
        if (email.isEmpty()) {
            binding.emailLayout.error = getString(R.string.email_is_required)
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailLayout.error = getString(R.string.invalid_email_format)
            isValid = false
        }

        // Password validation
        val password = binding.passwordLayout.editText?.text.toString().trim()
        if (password.isEmpty()) {
            binding.passwordLayout.error = getString(R.string.password_is_required)
            isValid = false
        } else if (password.length < 8) {
            binding.passwordLayout.error =
                getString(R.string.password_must_be_at_least_8_characters)
            isValid = false
        }

        // Confirm password validation
        val confirmPassword = binding.confirmPasswordLayout.editText?.text.toString().trim()
        if (confirmPassword != password) {
            binding.confirmPasswordLayout.error = getString(R.string.passwords_don_t_match)
            isValid = false
        }

        // Add validation for other required fields
        if (binding.firstNameLayout.editText?.text.isNullOrEmpty()) {
            binding.firstNameLayout.error = getString(R.string.first_name_is_required)
            isValid = false
        }

        if (binding.lastNameLayout.editText?.text.isNullOrEmpty()) {
            binding.lastNameLayout.error = getString(R.string.last_name_is_required)
            isValid = false
        }

        return isValid
    }

    private fun registerDoctor() {
        val doctorRequest = DoctorRegisterRequest(
            username = binding.usernameLayout.editText?.text.toString().trim(),
            email = binding.emailLayout.editText?.text.toString().trim(),
            password = binding.passwordLayout.editText?.text.toString().trim(),
            firstName = binding.firstNameLayout.editText?.text.toString().trim(),
            lastName = binding.lastNameLayout.editText?.text.toString().trim(),
            gender = binding.genderLayout.editText?.text.toString().trim(),
            dateOfBirth = binding.dateOfBirthLayout.editText?.text.toString().trim(),
            phone = binding.phoneLayout.editText?.text.toString().trim().toInt(),
            nationalId = binding.nationalIdLayout.editText?.text.toString().trim(),
            specialty = binding.specialtyLayout.editText?.text.toString().trim(),
            bio = binding.bioLayout.editText?.text.toString().trim(),
            licenseNumber = binding.licenseNumberLayout.editText?.text.toString().trim(),
            hospitalId = binding.hospitalIdLayout.editText?.text.toString().trim()
        )
        authViewModel.doctorRegister(doctorRequest)
    }

    private fun setupObservers() {
        authViewModel.stateShow.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AuthStateShow.Loading -> {
                    binding.loading.root.isVisible = true
                    binding.signUpBtn.isEnabled = false
                }

                is AuthStateShow.IsSuccess -> {
                    binding.loading.root.isVisible = false
                    binding.signUpBtn.isEnabled = true
                    if (state.hospitalDetails) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.doctor_register_succefully),
                            Toast.LENGTH_SHORT
                        ).show()
                        navigateToHomeScreen()
                    }
                }

                is AuthStateShow.ShowError -> {
                    binding.loading.root.isVisible = false
                    binding.signUpBtn.isEnabled = true
                    showError(state.errorMessage)
                }

                else -> {}
            }
        }
    }

    private fun navigateToHomeScreen() {
        findNavController().navigate(R.id.action_registerFragment_to_medicalServicesFragment)
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