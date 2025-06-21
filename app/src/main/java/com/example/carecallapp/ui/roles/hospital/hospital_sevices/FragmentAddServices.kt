package com.example.carecallapp.ui.roles.hospital.hospital_sevices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carecallapp.R
import com.example.carecallapp.data.repository.view_models.MyServicesViewModel
import com.example.carecallapp.data.repository.view_models.ServiceStateShow
import com.example.carecallapp.databinding.AddServiceFragmentBinding
import com.example.carecallapp.domain.ServiceTypeUtil
import com.example.carecallapp.domain.model.hospital.hospital_content.RoomType
import com.example.carecallapp.domain.model.hospital.hospital_content.ServiceRequest
import com.example.carecallapp.domain.model.hospital.hospital_content.ServiceType
import com.example.carecallapp.ui.utils.ShowState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAddServices : Fragment() {
    private var _binding: AddServiceFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyServicesViewModel by viewModels()
    private lateinit var serviceType: ServiceType
    lateinit var showState: ShowState


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = AddServiceFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showState= ShowState(requireContext())
        val args: FragmentAddServicesArgs by navArgs()
        serviceType = args.serviceType
        initListeners()
        observeData()
    }

    private fun observeData() {
        viewModel.stateShow.observe(viewLifecycleOwner) {
            when (it) {
                is ServiceStateShow.IsAddSuccess -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), "successfully", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    when (serviceType) {
                        ServiceType.Nursery, ServiceType.ICU -> {
                            val roomType = if (serviceType == ServiceType.Nursery)
                                RoomType.Nursery else RoomType.ICU
                            val action = FragmentAddServicesDirections
                                .actionFragmentAddServicesToRoomFragment(roomType)
                            findNavController().navigate(action)
                        }
                        ServiceType.BloodBank -> {
                            val action = FragmentAddServicesDirections
                                .actionFragmentAddServicesToBloodBankFragment()
                            findNavController().navigate(action)
                        }

                        else->{}
                    }}

                ServiceStateShow.Loading -> {
                    showLoading(true)
                }

                is ServiceStateShow.ShowError -> {
                    showState.showError(it.errorMessage)
                    showLoading(false)
                }
                else -> {}
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
