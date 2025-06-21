package com.example.carecallapp.ui.roles.ambulance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.example.carecallapp.R
import com.example.carecallapp.databinding.FragmentAmbulanceHomeBinding
import com.example.carecallapp.ui.roles.hospital.home.HospitalHomeFragmentDirections


class AmbulanceHomeFragment : Fragment() {

lateinit var binding:FragmentAmbulanceHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding=FragmentAmbulanceHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.supportFabBtn.supportFabBtn.setOnClickListener {
            val action =
                AmbulanceHomeFragmentDirections.actionGlobalToDialogSheetTechnicalSupport()
            findNavController().navigate(action)
        }
    }

}