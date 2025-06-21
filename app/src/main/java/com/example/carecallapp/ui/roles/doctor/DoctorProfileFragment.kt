package com.example.carecallapp.ui.roles.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.carecallapp.data.repository.view_models.MyPersonViewModel
import com.example.carecallapp.data.repository.view_models.PersonStateShow
import com.example.carecallapp.databinding.FragmentDoctorProfileBinding
import com.example.carecallapp.domain.model.person_service.doctor.DoctorProfile
import com.example.carecallapp.ui.utils.ShowState
import com.example.carecallapp.ui.utils.toBitMap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorProfileFragment : Fragment() {
    private lateinit var binding: FragmentDoctorProfileBinding
    private val viewModel: MyPersonViewModel by viewModels()
    private lateinit var doctorProfile: DoctorProfile
    lateinit var showState: ShowState

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoctorProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showState= ShowState(requireContext())
        viewModel.getDoctorProfile()
        observeDoctorState()
        initListeners()
    }

    private fun observeDoctorState() {
        viewModel.personState.observe(viewLifecycleOwner) {
            when (it) {
                is PersonStateShow.Loading -> showLoading(true)
                is PersonStateShow.IsGetSuccess -> {
                    doctorProfile = it.profile as DoctorProfile
                    initProfileUI(doctorProfile)
                    showLoading(false)
                }

                is PersonStateShow.ShowError -> {
                    showLoading(false)
                    showState .showError(it.errorMessage)
                }

                is PersonStateShow.IsNotFound -> {
                    showLoading(false)
                    showState.showError("Doctor profile not found")
                }

                else -> {}
            }
        }
    }

    private fun initListeners() {
        binding.editDoctorBtn.setOnClickListener {
            val action = DoctorProfileFragmentDirections
                .actionDoctorProfileFragmentToUpdateDoctorProfileFragment(doctorProfile)
            findNavController().navigate(action)

        }
    }

    private fun initProfileUI(profile: DoctorProfile) {
        "${profile.firstName} ${profile.lastName}".let { binding.tvDoctorName.text = it }
        binding.tvSpecialization.text = profile.bio
        binding.tvDoctorEmail.text = profile.email
        binding.tvDoctorPhone.text = profile.phone.toString()
        binding.tvDoctorGender.text = profile.gender
        binding.tvDoctorDateOfBirth.text = profile.dateOfBirth
        binding.tvDoctorNationalId.text = profile.nationalId
        if (profile.profilePicture!!.isNotEmpty()) {
            binding.ivDoctorPicture.root.setImageBitmap(profile.profilePicture.toBitMap())
        }    }

    private fun showLoading(show: Boolean) {
        binding.profileProgressBar.root.isVisible = show
    }

}
