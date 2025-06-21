package com.example.carecallapp.ui.roles.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.carecallapp.databinding.FragmentDoctorHomeBinding
import com.example.carecallapp.ui.roles.hospital.home.HospitalHomeFragmentDirections


class DoctorHomeFragment : Fragment() {
lateinit var binding:FragmentDoctorHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentDoctorHomeBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.supportFabBtn.supportFabBtn.setOnClickListener {
            val action =
                HospitalHomeFragmentDirections.actionGlobalToDialogSheetTechnicalSupport()
            findNavController().navigate(action)
        }
    }
}