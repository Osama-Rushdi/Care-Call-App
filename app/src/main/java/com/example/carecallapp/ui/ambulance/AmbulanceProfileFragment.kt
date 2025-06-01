package com.example.carecallapp.ui.ambulance

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.carecallapp.data.repository.view_models.MyPersonViewModel
import com.example.carecallapp.data.repository.view_models.PersonStateShow
import com.example.carecallapp.databinding.FragmentAmbulanceProfileBinding
import com.example.carecallapp.domain.model.PersonService.ambulance.AmbulanceProfile
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AmbulanceProfileFragment : Fragment() {
    private lateinit var binding: FragmentAmbulanceProfileBinding
    private val viewModel: MyPersonViewModel by viewModels()
    private lateinit var ambulanceProfile: AmbulanceProfile

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAmbulanceProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAmbulanceProfile()
        observeAmbulanceState()
        initListeners()
    }

    private fun observeAmbulanceState() {
        viewModel.personState.observe(viewLifecycleOwner) {
            when (it) {
                is PersonStateShow.Loading -> showLoading(true)
                is PersonStateShow.IsGetSuccess -> {
                    ambulanceProfile = it.profile as AmbulanceProfile
                    initProfileUI(ambulanceProfile)
                    showLoading(false)
                }
                is PersonStateShow.ShowError -> {
                    showLoading(false)
                    showError(it.errorMessage)
                }
                is PersonStateShow.IsNotFound -> {
                    showLoading(false)
                    showError("Ambulance profile not found")
                }
                else -> {}
            }
        }
    }

    private fun initListeners() {
//        binding.editAmbulanceBtn.setOnClickListener {
//            val action = AmbulanceProfileFragmentDirections
//                .actionAmbulanceProfileFragmentToAmbulanceUpdateProfileFragment(ambulanceProfile)
//            findNavController().navigate(action)
//        }
    }

    private fun initProfileUI(profile: AmbulanceProfile) {
        binding.tvAmbulanceName.text = profile.username
        binding.tvEmail.text = profile.email
        binding.tvPhone.text = profile.phone.toString()
        binding.tvGender.text = profile.gender
        binding.tvNationalId.text = profile.nationalId
        binding.tvUsername.text = profile.username
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
