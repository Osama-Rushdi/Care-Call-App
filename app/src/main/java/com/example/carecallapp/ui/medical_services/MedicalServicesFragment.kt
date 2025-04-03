package com.example.carecallapp.ui.medical_services

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.renderscript.Sampler.Value
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.carecallapp.data.repository.view_model.MyAccountsViewModel
import com.example.carecallapp.databinding.FragmentMedicalServicesBinding
import com.example.carecallapp.domain.repository.HospitalRepository
import com.example.carecallapp.ui.home.HomeViewModel
import com.example.carecallapp.ui.medical_services.utils.MedicalServicesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicalServicesFragment : Fragment() {

    private var _binding: FragmentMedicalServicesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MedicalServicesAdapter
    private var isDoctorSelect: Boolean? = null
 private val viewModel:MyAccountsViewModel by viewModels<MyAccountsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMedicalServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        adapter = MedicalServicesAdapter(viewModel.initAdapter.value!!) {
            selectItem()
        }

    }

    private fun initListeners() {

        binding.doctorSelectCV.setOnClickListener {
            changeDoctorSelect()
        }
        binding.ambulanceSelectCV.setOnClickListener {
            changeAmbulanceSelect()
        }

    }

    private fun changeAmbulanceSelect(change: Boolean = true) {
        if (change) {
            binding.ambulanceSelectCV.setBackgroundColor(Color.BLUE)
            binding.ambulanceAccountsTV.setTextColor(Color.WHITE)
            isDoctorSelect = false
        } else {
            binding.ambulanceSelectCV.setBackgroundColor(Color.WHITE)
            binding.ambulanceAccountsTV.setTextColor(Color.BLUE)
        }

    }

    private fun changeDoctorSelect(change: Boolean = true) {
        if (change) {
            binding.doctorSelectCV.setBackgroundColor(Color.BLUE)
            binding.doctorAccountsTV.setTextColor(Color.WHITE)
            isDoctorSelect = true
        } else {
            binding.doctorSelectCV.setBackgroundColor(Color.WHITE)
            binding.doctorAccountsTV.setTextColor(Color.BLUE)
        }
    }

    private fun selectItem(): Boolean {
        if (isDoctorSelect == true)
            return true
        else if (isDoctorSelect == false)
            return false
        return true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}