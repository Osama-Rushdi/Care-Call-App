package com.example.carecallapp.ui.doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.carecallapp.R

class UpdateDoctorProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_doctor_profile, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize your views and listeners here
        // For example, you can set up a button click listener to update the profile
        // binding.updateProfileButton.setOnClickListener { updateDoctorProfile() }
    }
}