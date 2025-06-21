package com.example.carecallapp.ui.roles.doctor

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carecallapp.data.repository.view_models.MyPersonViewModel
import com.example.carecallapp.data.repository.view_models.PersonStateShow
import com.example.carecallapp.databinding.FragmentUpdateDoctorProfileBinding
import com.example.carecallapp.domain.model.person_service.doctor.DoctorProfile
import com.example.carecallapp.domain.model.hospital.hospital_profile.HospitalResponse
import com.example.carecallapp.ui.roles.hospital.profile.HospitalUpdateProfileFragmentArgs
import com.example.carecallapp.ui.utils.bitmapToBase64
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class UpdateDoctorProfileFragment : Fragment() {

    private lateinit var binding: FragmentUpdateDoctorProfileBinding
    private val viewModel: MyPersonViewModel by viewModels()
    private lateinit var doctorProfile: DoctorProfile
    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>
    private var selectedBase64Image: String? = null
    private var photo: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateDoctorProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: UpdateDoctorProfileFragmentArgs by navArgs()
        doctorProfile = args.doctor
        initImageLauncher()

        setupDatePicker()
        initProfileDetails(doctorProfile)
        initListeners()
        observeState()
        setupGenderDropdown()
    }

    private fun initImageLauncher() {
        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                val bitmap = uriToBitmap(it)
                selectedBase64Image = bitmapToBase64(bitmap)
                binding.profileImage.setImageBitmap(bitmap) // عرض الصورة
            }
        }
    }

    private fun setupDatePicker() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            val displayedDate =
                "${year}-${if (month + 1 < 10) "0" + (month + 1) else (month + 1)}-${if (day < 10) "0$day" else (day)}"
            binding.etDateOfBirth.setText(displayedDate)
        }
        binding.etDateOfBirth.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                datePicker,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener {
            try {
                photo = selectedBase64Image ?: doctorProfile.profilePicture!!

                viewModel.updateDoctorProfile(
                    doctorProfile = DoctorProfile(
                        profilePicture = photo,
                        firstName = binding.etFirstName.text.toString(),
                        lastName = binding.etLastName.text.toString(),
                        nationalId = binding.etNationalId.text.toString(),
                        gender = binding.etGender.text.toString(),
                        phone = binding.etPhone.text.toString().trim().toInt(),
                        dateOfBirth = binding.etDateOfBirth.text.toString(),
                        email = binding.etEmail.text.toString(),
                        username = binding.etUsername.text.toString(),
                        specialty = binding.etSpecialty.text.toString(),
                        hospitalId = doctorProfile.hospitalId,
                        licenseNumber = binding.etLicenseNumber.text.toString()
                    )
                )
            } catch (e: Exception) {
                Log.d("kkk", "initListeners:${e.message} ")
            }
        }
        Log.d("kkk", "photo: $photo")

        binding.profileImage.setOnClickListener {
            openGallery()
        }
        binding.btnCancel.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun observeState() {
        viewModel.personState.observe(viewLifecycleOwner) {
            when (it) {
                is PersonStateShow.Loading -> {
                    Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                }


                is PersonStateShow.ShowError -> {
                    Log.d("kkk", "Error: ${it.errorMessage}")
                    Toast.makeText(
                        requireContext(),
                        "Error: ${it.errorMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is PersonStateShow.IsUpdateSuccess -> {
                    Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                else -> {}
            }
        }
    }

    private fun setupGenderDropdown() {
        val genders = listOf("Male", "Female")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, genders)
        binding.etGender.setAdapter(adapter)
    }

    private fun initProfileDetails(doctorProfile: DoctorProfile) {
        doctorProfile.apply {
            binding.etEmail.setText(email)
            binding.etPhone.setText(phone.toString())
            binding.etGender.setText(gender)
            binding.etFirstName.setText(firstName)
            binding.etLastName.setText(lastName)
            binding.etDateOfBirth.setText(dateOfBirth)
            binding.etNationalId.setText(nationalId)
            binding.etUsername.setText(username)
            binding.etSpecialty.setText(specialty)
            binding.etLicenseNumber.setText(licenseNumber)
            binding.profileImage.setImageBitmap(profilePicture?.let {
                val imageBytes = Base64.decode(it, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            })
        }
    }

    private fun uriToBitmap(uri: Uri): Bitmap {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(inputStream)
    }

    private fun openGallery() {
        imagePickerLauncher.launch("image/*")
    }
}