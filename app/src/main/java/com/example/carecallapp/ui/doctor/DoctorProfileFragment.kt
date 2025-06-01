package com.example.carecallapp.ui.doctor

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.carecallapp.data.repository.view_models.MyPersonViewModel
import com.example.carecallapp.data.repository.view_models.PersonStateShow
import com.example.carecallapp.databinding.FragmentDoctorProfileBinding
import com.example.carecallapp.domain.model.PersonService.doctor.DoctorProfile
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorProfileFragment : Fragment() {
    private lateinit var binding: FragmentDoctorProfileBinding
    private val viewModel: MyPersonViewModel by viewModels()
    private lateinit var doctorProfile: DoctorProfile

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoctorProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
                    showError(it.errorMessage)
                }
                is PersonStateShow.IsNotFound -> {
                    showLoading(false)
                    showError("Doctor profile not found")
                }
                else -> {}
            }
        }
    }

    private fun initListeners() {
        binding.editDoctorBtn.setOnClickListener {
//            val action = DoctorProfileFragmentDirections
//                .actionDoctorProfileFragmentToDoctorUpdateProfileFragment(doctorProfile)
//            findNavController().navigate(action)
//
        }
    }

    private fun initProfileUI(profile: DoctorProfile) {
        binding.tvDoctorName.text = "${profile.firstName} ${profile.lastName}"
        binding.tvSpecialization.text = profile.bio
        binding.tvDoctorEmail.text = profile.email
        binding.tvDoctorPhone.text = profile.phone.toString()
       binding.tvDoctorGender.text = profile.gender
        binding.tvDoctorDateOfBirth.text = profile.dateOfBirth
        binding.tvDoctorNationalId.text = profile.nationalId
    }

    private fun showLoading(show: Boolean) {
        binding.profileProgressBar.root.isVisible = show
    }

    private fun showError(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
