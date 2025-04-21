package com.example.carecallapp.ui.hospital.profile

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.carecallapp.data.mappers.toHospitalResponse
import com.example.carecallapp.data.mappers.toHospitalResponseDM
import com.example.carecallapp.data.repository.view_models.MyProfileViewModel
import com.example.carecallapp.data.repository.view_models.ProfileStateShow
import com.example.carecallapp.databinding.FragmentHospitalProfileBinding
import com.example.carecallapp.databinding.FragmentHospitalUpdateProfileBinding
import com.example.carecallapp.domain.model.hospital_profile.HospitalResponse
import com.example.carecallapp.ui.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HospitalUpdateProfileFragment : Fragment() {
    private lateinit var binding: FragmentHospitalUpdateProfileBinding
    private val viewModel: MyProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHospitalUpdateProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initProfileDetails(getBundleHospitalDetails())
        initListeners()
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener {
            try {
                viewModel.updateDetails(
                    hospitalResponse = HospitalResponse(
                        profilePicture = "",
                        firstName = binding.etFirstName.text.toString(),
                        lastName = binding.etLastName.text.toString(),
                        website = binding.etWebsite.text.toString(),
                        nationalId = binding.etNationalId.text.toString(),
                        gender = binding.etGender.text.toString(),
                        phone = binding.etPhone.text.toString().trim().toInt(),
                        dateOfBirth = binding.etDateOfBirth.text.toString(),
                        email = binding.etEmail.text.toString(),
                        username = binding.etUsername.text.toString()
                    )
                )
            } catch (e: Exception) {
                Log.d("kkk", "initListeners:${e.message} ")
            }
            viewModel.stateShow.observe(viewLifecycleOwner) {
                when (it) {
                    is ProfileStateShow.IsSuccess -> {
                        parentFragmentManager.popBackStack()
                        Toast.makeText(
                            requireContext(),
                            "Done",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    ProfileStateShow.Loading -> {
                        Toast.makeText(
                            requireContext(),
                            "Loading",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is ProfileStateShow.ShowError -> Log.d("kkk", "initListeners:${it.errorMessage} ")
                }
            }
        }
        binding.btnCansel.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

    }

    private fun getBundleHospitalDetails(): HospitalResponse {
        val hospitalDetails: HospitalResponse =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                arguments?.getSerializable(
                    Constants.HOSPITAL_PROFILE_KEY,
                    HospitalResponse::class.java
                )!!
            else
                arguments?.getSerializable(Constants.HOSPITAL_PROFILE_KEY) as HospitalResponse
        return hospitalDetails
    }

    private fun initProfileDetails(hospitalDetails: HospitalResponse) {
        hospitalDetails.apply {
            binding.etEmail.setText(email)
            binding.etPhone.setText(phone.toString())
            binding.etGender.setText(gender)
            binding.etWebsite.setText(website)
            binding.etFirstName.setText(firstName)
            binding.etLastName.setText(lastName)
            binding.etDateOfBirth.setText(dateOfBirth)
            binding.etNationalId.setText(nationalId)
            binding.etUsername.setText(username)


        }

    }

}