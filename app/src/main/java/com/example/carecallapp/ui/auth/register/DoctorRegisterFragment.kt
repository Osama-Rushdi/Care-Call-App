package com.example.carecallapp.ui.auth.register

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
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
import com.example.carecallapp.data.repository.view_models.AuthStateShow
import com.example.carecallapp.data.repository.view_models.MyAuthViewModel
import com.example.carecallapp.databinding.FragmentDoctorRegisterBinding
import com.example.carecallapp.domain.model.auth.DoctorRegisterRequest
import com.example.carecallapp.ui.utils.Constants
import com.example.carecallapp.ui.utils.ShowState
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class DoctorRegisterFragment : Fragment() {
    private var _binding: FragmentDoctorRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: MyAuthViewModel by viewModels()
    private lateinit var sharedPref: SharedPreferences
    lateinit var showState: ShowState

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
        showState = ShowState(requireContext())
        sharedPref =
            requireContext().getSharedPreferences(Constants.SHARED_TOKEN_NAME, Context.MODE_PRIVATE)

        initDatePicker()
        initGenderDropdown()
        initObservers()
        initListeners()
        initTextWatchers()

    }

    private fun initTextWatchers() {
        with(binding) {
            usernameLayout.editText?.addTextChangedListener { usernameLayout.error = null }
            emailLayout.editText?.addTextChangedListener { emailLayout.error = null }
            passwordLayout.editText?.addTextChangedListener { passwordLayout.error = null }
            confirmPasswordLayout.editText?.addTextChangedListener {
                confirmPasswordLayout.error = null
            }
            firstNameLayout.editText?.addTextChangedListener { firstNameLayout.error = null }
            lastNameLayout.editText?.addTextChangedListener { lastNameLayout.error = null }
            phoneLayout.editText?.addTextChangedListener { phoneLayout.error = null }
            nationalIdLayout.editText?.addTextChangedListener { nationalIdLayout.error = null }
            licenseNumberLayout.editText?.addTextChangedListener {
                licenseNumberLayout.error = null
            }
            dateOfBirthLayout.editText?.addTextChangedListener { dateOfBirthLayout.error = null }
            genderLayout.editText?.addTextChangedListener { genderLayout.error = null }
        }
    }

    private fun initListeners() {
        binding.signUpBtn.setOnClickListener {
            if (validateInputs()) {
                registerDoctor()
            }
        }
    }

    private fun initGenderDropdown() {
        val genders = listOf("Male", "Female")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, genders)
        val autoComplete = (binding.genderLayout.editText) as AutoCompleteTextView
        autoComplete.setAdapter(adapter)
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        binding.apply {

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
                passwordLayout.error =
                    getString(R.string.password_must_be_at_least_8_characters)
                isValid = false
            }

            // Confirm password validation
            val confirmPassword = confirmPasswordLayout.editText?.text.toString().trim()
            if (confirmPassword != password) {
                confirmPasswordLayout.error = getString(R.string.passwords_don_t_match)
                isValid = false
            }

            // Add validation for other required fields
            if (firstNameLayout.editText?.text.isNullOrEmpty()) {
                firstNameLayout.error = getString(R.string.first_name_is_required)
                isValid = false
            }

            if (lastNameLayout.editText?.text.isNullOrEmpty()) {
                lastNameLayout.error = getString(R.string.last_name_is_required)
                isValid = false
            }
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

            // license number validation
            if (licenseNumberLayout.editText?.text.isNullOrEmpty()) {
                licenseNumberLayout.error = getString(R.string.vehicle_number_required)
                isValid = false
            }

            return isValid
        }
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
            hospitalId = sharedPref.getString(Constants.USER_ID_KEY, null)!!,
        )
        authViewModel.doctorRegister(doctorRequest)
    }

    private fun initObservers() {
        authViewModel.stateShow.observe(viewLifecycleOwner) { state ->
            binding.loading.root.isVisible = false
            when (state) {
                is AuthStateShow.Loading -> {
                    binding.loading.root.isVisible = true
                    binding.signUpBtn.isEnabled = false
                }

                is AuthStateShow.IsRegisterSuccess -> {
                    binding.loading.root.isVisible = false
                    binding.signUpBtn.isEnabled = true
                    if (state.isSuccess) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.doctor_register_succefully),
                            Toast.LENGTH_SHORT
                        ).show()
                        navigateToMedicalScreen()
                    }
                }

                is AuthStateShow.ShowError -> {
                    binding.loading.root.isVisible = false
                    binding.signUpBtn.isEnabled = true
                    showState.showError(state.errorMessage)
                }

                else -> {}
            }
        }
    }

    private fun navigateToMedicalScreen() {
        val action =
            DoctorRegisterFragmentDirections.actionDoctorRegisterFragmentToMedicalServicesFragment()
        findNavController().navigate(action)
    }


    private fun initDatePicker() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            val displayedDate =
                "${year}-${if (month + 1 < 10) "0" + (month + 1) else (month + 1)}-${if (day < 10) "0$day" else (day)}"
            binding.dateOfBirthLayout.editText?.setText(displayedDate)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}