package com.example.carecallapp.ui.roles.hospital.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.carecallapp.R
import com.example.carecallapp.data.repository.view_models.MyProfileViewModel
import com.example.carecallapp.data.repository.view_models.ProfileStateShow
import com.example.carecallapp.databinding.FragmentHospitalProfileBinding
import com.example.carecallapp.domain.model.hospital.hospital_profile.HospitalResponse
import com.example.carecallapp.ui.utils.ShowState
import com.example.carecallapp.ui.utils.formatDateTimeToShow
import com.example.carecallapp.ui.utils.toBitMap
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HospitalProfileFragment : Fragment() {
    private lateinit var binding: FragmentHospitalProfileBinding
    private val viewModel: MyProfileViewModel by viewModels()
    private lateinit var saveData: HospitalResponse
    lateinit var showState: ShowState
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHospitalProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showState= ShowState(requireContext())
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
                    showState.showError(it.errorMessage)
                    binding.editProfileBtn.isClickable = false
                }

                else -> {}
            }
        }
    }

    private fun initListener() {
        binding.editProfileBtn.setOnClickListener {
            if (saveData.equals(null)) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.some_thing_go_wrong), Toast.LENGTH_SHORT
                ).show()
            } else {

                val action =
                    HospitalProfileFragmentDirections.actionHospitalProfileFragmentToHospitalUpdateProfileFragment(
                        saveData
                    )
                findNavController().navigate(action)
            }
        }
        binding.webSiteLayout.setOnClickListener {
            var url = binding.tvWebsite.text.toString().trim()

            if (url.isNotEmpty()) {
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://$url"
                }
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)}
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
                binding.tvDateOfBirth.text = formatDateTimeToShow(dateOfBirth)
                binding.tvNationalId.text = nationalId
                binding.tvUsername.text = username
                if (profilePicture.isNotEmpty()) {
                    binding.ivProfilePicture.root.setImageBitmap(profilePicture.toBitMap())
                }


            }
        }




    }