package com.example.carecallapp.ui.auth.register

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.carecallapp.R
import com.example.carecallapp.data.model.auth.AmbulanceRegisterRequestDM
import com.example.carecallapp.data.repository.view_models.AuthStateShow
import com.example.carecallapp.data.repository.view_models.MyAuthViewModel
import com.example.carecallapp.databinding.FragmentAmbulanceRegisterBinding
import com.google.android.material.internal.ViewUtils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AmbulanceRegisterFragment : Fragment() {

    private var _binding: FragmentAmbulanceRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: MyAuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAmbulanceRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupGenderDropdown()
        setupDatePicker()
        setupTextWatchers()
        setupObservers()
        initListeners()
    }

    private fun setupGenderDropdown() {
        val genders = listOf("Male", "Female", "Other")
        val adapter = ArrayAdapter(requireContext(), com.zerobranch.layout.R.layout.support_simple_spinner_dropdown_item, genders)
        (binding.genderLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun setupDatePicker() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            binding.dateOfBirthLayout.editText?.setText(
                "${day}/${month + 1}/${year}"
            )
        }

        binding.dateOfBirthLayout.editText?.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                datePicker,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupTextWatchers() {
        with(binding) {
            usernameLayout.editText?.addTextChangedListener { usernameLayout.error = null }
            emailLayout.editText?.addTextChangedListener { emailLayout.error = null }
            passwordLayout.editText?.addTextChangedListener { passwordLayout.error = null }
            confirmPasswordLayout.editText?.addTextChangedListener { confirmPasswordLayout.error = null }
            firstNameLayout.editText?.addTextChangedListener { firstNameLayout.error = null }
            lastNameLayout.editText?.addTextChangedListener { lastNameLayout.error = null }
            phoneLayout.editText?.addTextChangedListener { phoneLayout.error = null }
            nationalIdLayout.editText?.addTextChangedListener { nationalIdLayout.error = null }
            vehicleNumberLayout.editText?.addTextChangedListener { vehicleNumberLayout.error = null }
          //  hospitalIdLayout.editText?.addTextChangedListener { hospitalIdLayout.error = null }
        }
    }

    private fun initListeners() {
        binding.signUpBtn.setOnClickListener {
            if (validateInputs()) {
              //  registerAmbulance()
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        with(binding) {
            // Username validation
            if (usernameLayout.editText?.text.isNullOrEmpty()) {
                usernameLayout.error = getString(R.string.username_is_required)
                isValid = false
            }

            // Email validation
            val email = emailLayout.editText?.text.toString().trim()
            if (email.isEmpty()) {
                emailLayout.error = getString(R.string.email_is_required)
                isValid = false
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailLayout.error = getString(R.string.invalid_email_format)
                isValid = false
            }

            // Password validation
            val password = passwordLayout.editText?.text.toString().trim()
            if (password.isEmpty()) {
                passwordLayout.error = getString(R.string.password_is_required)
                isValid = false
            } else if (password.length < 8) {
                passwordLayout.error = getString(R.string.password_must_be_at_least_8_characters)
                isValid = false
            }

            // Confirm password validation
            val confirmPassword = confirmPasswordLayout.editText?.text.toString().trim()
            if (confirmPassword != password) {
                confirmPasswordLayout.error = getString(R.string.passwords_don_t_match)
                isValid = false
            }

            // First name validation
            if (firstNameLayout.editText?.text.isNullOrEmpty()) {
                firstNameLayout.error = getString(R.string.first_name_is_required)
                isValid = false
            }

            // Last name validation
            if (lastNameLayout.editText?.text.isNullOrEmpty()) {
                lastNameLayout.error = getString(R.string.last_name_is_required)
                isValid = false
            }

            // Gender validation
            if (genderLayout.editText?.text.isNullOrEmpty()) {
                genderLayout.error = getString(R.string.gender_is_required)
                isValid = false
            }

            // Date of birth validation
            if (dateOfBirthLayout.editText?.text.isNullOrEmpty()) {
                dateOfBirthLayout.error = "Date of birth required "
                isValid = false
            }

            // Phone validation
            val phone = phoneLayout.editText?.text.toString().trim()
            if (phone.isEmpty()) {
                phoneLayout.error = getString(R.string.phone_is_required)
                isValid = false
            } else if (phone.length < 10) {
                phoneLayout.error = getString(R.string.invalid_phone_number)
                isValid = false
            }

            // National ID validation
            if (nationalIdLayout.editText?.text.isNullOrEmpty()) {
                nationalIdLayout.error = getString(R.string.national_id_required)
                isValid = false
            }

            // Vehicle number validation
            if (vehicleNumberLayout.editText?.text.isNullOrEmpty()) {
                vehicleNumberLayout.error = getString(R.string.vehicle_number_required)
                isValid = false
            }


        }
        return isValid
    }

//    private fun registerAmbulance() {
//        hideKeyboard()
//
//        val ambulanceRequest = AmbulanceRegisterRequest(
//            username = binding.usernameLayout.editText?.text.toString().trim(),
//            email = binding.emailLayout.editText?.text.toString().trim(),
//            password = binding.passwordLayout.editText?.text.toString().trim(),
//            firstName = binding.firstNameLayout.editText?.text.toString().trim(),
//            lastName = binding.lastNameLayout.editText?.text.toString().trim(),
//            gender = binding.genderLayout.editText?.text.toString().trim(),
//            dateOfBirth = binding.dateOfBirthLayout.editText?.text.toString().trim(),
//            phone = binding.phoneLayout.editText?.text.toString().trim().toIntOrNull() ?: 0,
//            nationalId = binding.nationalIdLayout.editText?.text.toString().trim(),
//            vehicleNumber = binding.vehicleNumberLayout.editText?.text.toString().trim(),
//            hospitalId = binding.hospitalIdLayout.editText?.text.toString().trim(),
//            confirmPassword = binding.confirmPasswordLayout.editText?.text.toString().trim()
//        )
//
//        authViewModel.ambulanceRegister(ambulanceRequest)
//    }

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
                            getString(R.string.ambulance_register_succefully),
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
        findNavController().navigate(R.id.action_ambulanceRegisterFragment_to_medicalServicesFragment)
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