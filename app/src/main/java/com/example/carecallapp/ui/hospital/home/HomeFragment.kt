package com.example.carecallapp.ui.hospital.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.carecallapp.data.repository.view_models.MyServicesViewModel
import com.example.carecallapp.data.repository.view_models.ServiceStateShow
import com.example.carecallapp.databinding.FragmentHomeBinding
import com.example.carecallapp.domain.model.hospital.hospital_content.ServiceType
import com.example.carecallapp.ui.utils.navigateToService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyServicesViewModel by viewModels()
    private lateinit var type: ServiceType
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        observe()
    }

    private fun observe() {
        viewModel.stateShow.observe(viewLifecycleOwner) {
            when (it) {
                is ServiceStateShow.IsSearchSuccess -> {
                    if (it.success) {
                        navigateToService(type,findNavController())
                    } else {
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToFragmentAddServices(type)
                        findNavController().navigate(action)
                    }
                }
                else -> {}
            }
        }
    }


    private fun initListeners() {
        binding.nurseryCD.setOnClickListener {

            type = ServiceType.Nursery
            viewModel.searchServiceByNameUseCase(type.name)

        }
        binding.emergencyRoomCD.setOnClickListener {
            type = ServiceType.ICU
            viewModel.searchServiceByNameUseCase(type.name)
        }
        binding.bloodItemCD.setOnClickListener {
            type = ServiceType.BloodBank
            viewModel.searchServiceByNameUseCase(type.name)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}