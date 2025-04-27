package com.example.carecallapp.ui.hospital.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carecallapp.R
import com.example.carecallapp.databinding.FragmentHomeBinding
import com.example.carecallapp.ui.hospital.hospital_sevices.blood_bank.BloodBankFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.nurseryCD.setOnClickListener { }
        binding.bloodItemCD.setOnClickListener {
          parentFragmentManager
                .beginTransaction()
              .replace(R.id.fragmentContainer,BloodBankFragment())
                .commit()
        }
        binding.emergencyRoomCD.setOnClickListener { }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}