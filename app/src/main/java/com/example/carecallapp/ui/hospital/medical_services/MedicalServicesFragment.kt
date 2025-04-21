package com.example.carecallapp.ui.hospital.medical_services

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.carecallapp.data.repository.view_models.MyAccountsViewModel
import com.example.carecallapp.data.repository.view_models.AccountsStateShow
import com.example.carecallapp.databinding.FragmentMedicalServicesBinding
import com.example.carecallapp.domain.Types
import com.example.carecallapp.ui.hospital.medical_services.utils.MedicalServicesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicalServicesFragment : Fragment() {

    private var _binding: FragmentMedicalServicesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MedicalServicesAdapter
    private var isDoctorSelect: Boolean = true
    private val viewModel: MyAccountsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicalServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()

        viewModel.initAdapter.observe(viewLifecycleOwner) { accounts ->
            adapter = MedicalServicesAdapter(accounts) {
                selectItem()
            }
            binding.AccountsRecyclerView.adapter = adapter
        }
        viewModel.stateShow.observe(viewLifecycleOwner) {
            when (it) {
                is AccountsStateShow.IsSuccess -> {
                    binding.AccountsRecyclerView.isVisible = true
                    viewModel.initAdapter.value = it.accounts
                }

                AccountsStateShow.Loading -> showLoading(true)
                is AccountsStateShow.ShowError -> {
                    showError(it.errorMessage)
                    showLoading(false)
                }
            }
        }
        viewModel.showAccounts(Types.peopleTypeUrl(true))
    }

    private fun initListeners() {
        binding.doctorSelectCV.setOnClickListener {
            changeDoctorSelect()
            changeAmbulanceSelect(false)
            viewModel.showAccounts(Types.peopleTypeUrl(true))
        }
        binding.ambulanceSelectCV.setOnClickListener {
            viewModel.showAccounts(Types.peopleTypeUrl(false))
            changeAmbulanceSelect()
            changeDoctorSelect(false)
        }
    }

    private fun showLoading(isVisible: Boolean) {
        binding.AccountLoadingProgressBar.isVisible = isVisible
    }
    private fun showError(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
        binding.AccountsRecyclerView.isVisible = false
    }

    private fun changeAmbulanceSelect(change: Boolean = true) {
        if (change) {
            binding.ambulanceSelectCV.setCardBackgroundColor(Color.parseColor("#01007F"))
            binding.ambulanceAccountsTV.setTextColor(Color.WHITE)
            isDoctorSelect = false
        } else {
            binding.ambulanceSelectCV.setCardBackgroundColor(Color.WHITE)
            binding.ambulanceAccountsTV.setTextColor(Color.parseColor("#01007F"))
        }
    }

    private fun changeDoctorSelect(change: Boolean = true) {
        if (change) {
            binding.doctorSelectCV.setCardBackgroundColor(Color.parseColor("#01007F"))
            binding.doctorAccountsTV.setTextColor(Color.WHITE)
            isDoctorSelect = true
        } else {
            binding.doctorSelectCV.setCardBackgroundColor(Color.WHITE)
            binding.doctorAccountsTV.setTextColor(Color.parseColor("#01007F"))
        }
    }

    private fun selectItem(): Boolean {
        return isDoctorSelect
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
