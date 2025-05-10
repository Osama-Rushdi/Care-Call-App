package com.example.carecallapp.ui.hospital.hospital_sevices

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.carecallapp.R
import com.example.carecallapp.data.repository.view_models.MyServicesViewModel
import com.example.carecallapp.data.repository.view_models.ServiceStateShow
import com.example.carecallapp.databinding.AddServiceFragmentBinding
import com.example.carecallapp.domain.ServiceTypeUtil
import com.example.carecallapp.domain.model.hospital_content.RoomType
import com.example.carecallapp.domain.model.hospital_content.ServiceRequest
import com.example.carecallapp.domain.model.hospital_content.ServiceType
import com.example.carecallapp.ui.hospital.hospital_sevices.blood_bank.BloodBankFragment
import com.example.carecallapp.ui.hospital.hospital_sevices.room_and_nursery.RoomFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAddServices(private val serviceType: ServiceType) : Fragment() {
    private var _binding: AddServiceFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyServicesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = AddServiceFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        observeData()
    }

    private fun observeData() {
        viewModel.stateShow.observe(viewLifecycleOwner) {
            when (it) {
                is ServiceStateShow.IsAddSuccess -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), "successfully", Toast.LENGTH_SHORT).show()
                    navigationToServiceFragments()
                }

                ServiceStateShow.Loading -> {
                    showLoading(true)
                }

                is ServiceStateShow.ShowError -> {
                    showError(it.errorMessage)
                    showLoading(false)
                }
                else -> {}
            }
        }
    }

    private fun navigationToServiceFragments() {
        when (serviceType.name) {
            ServiceType.ICU.name -> {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, RoomFragment(RoomType.ICU))
                    .commit()
            }

            ServiceType.BloodBank.name -> {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, BloodBankFragment())
                    .commit()
            }

            ServiceType.Nursery.name -> {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, RoomFragment(RoomType.Nursery))
                    .commit()
            }
        }
    }

    private fun initListeners() {
        binding.serviceTypeET.editText!!.setText(serviceType.name)
        binding.addServiceButton.setOnClickListener {
            viewModel.addService(
                ServiceRequest(
                    binding.servicePrice.editText!!.text.toString().toInt(),
                    binding.serviceTypeET.editText!!.text.toString(),
                    binding.serviceDescription.editText!!.text.toString(),
                    ServiceTypeUtil.getId(serviceType.name)
                )
            )
        }
    }

    private fun showLoading(isVisible: Boolean) {
        binding.addServiceLoading.root.isVisible = isVisible
    }

    private fun showError(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
