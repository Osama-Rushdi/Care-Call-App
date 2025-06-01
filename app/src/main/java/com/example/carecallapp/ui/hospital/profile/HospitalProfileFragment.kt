package com.example.carecallapp.ui.hospital.profile

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.carecallapp.R
import com.example.carecallapp.data.repository.view_models.MyProfileViewModel
import com.example.carecallapp.data.repository.view_models.ProfileStateShow
import com.example.carecallapp.databinding.FragmentHospitalProfileBinding
import com.example.carecallapp.domain.model.hospital.hospital_profile.HospitalResponse
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale


@AndroidEntryPoint
class HospitalProfileFragment : Fragment() {
    private lateinit var binding: FragmentHospitalProfileBinding
    private val viewModel: MyProfileViewModel by viewModels()
    private lateinit var saveData: HospitalResponse
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHospitalProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.showDetails()
        observer()
        initListener()
    }

    private fun observer() {
        viewModel.stateShow.observe(viewLifecycleOwner) {
            when (it) {
                is ProfileStateShow.IsSuccess -> {
                    saveData = it.hospitalDetails
                    showLoading(false)
                    initProfileDetails(it.hospitalDetails)
                    binding.editProfileBtn.isClickable = true
                }

                ProfileStateShow.Loading -> {
                    showLoading(true)
                    binding.editProfileBtn.isClickable = false
                }

                is ProfileStateShow.ShowError -> {
                    showError(it.errorMessage)
                    binding.editProfileBtn.isClickable = false
                }
            }
        }
    }

    private fun initListener() {
        binding.editProfileBtn.setOnClickListener {
            if (saveData.equals(null)) {
                Toast.makeText(requireContext(),
                    getString(R.string.some_thing_go_wrong), Toast.LENGTH_SHORT).show()
            } else {

                val action =
                    HospitalProfileFragmentDirections.actionHospitalProfileFragmentToHospitalUpdateProfileFragment(
                        saveData
                    )
                findNavController().navigate(action)
            }
        }
    }

    private fun showLoading(isVisible: Boolean) {
        binding.profileLoadingProgressBar.isVisible = isVisible
    }

    private fun initProfileDetails(hospitalDetails: HospitalResponse) {
        hospitalDetails.apply {
            binding.tvEmail.text = email
            binding.tvPhone.text = phone.toString()
            binding.tvGender.text = gender
            binding.tvWebsite.text = website
            "$firstName $lastName".let { binding.tvFullName.text = it }
            binding.tvDateOfBirth.text = formatDateTime(dateOfBirth)
            binding.tvNationalId.text = nationalId
            binding.tvUsername.text = username
        }
    }

    private fun showError(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun formatDateTime(input: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())

            val date = inputFormat.parse(input)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            input
        }
    }
}