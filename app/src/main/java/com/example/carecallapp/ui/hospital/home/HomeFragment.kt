package com.example.carecallapp.ui.hospital.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carecallapp.R
import com.example.carecallapp.databinding.FragmentHomeBinding
import com.example.carecallapp.domain.model.hospital_content.RoomType
import com.example.carecallapp.ui.hospital.hospital_sevices.blood_bank.BloodBankFragment
import com.example.carecallapp.ui.hospital.hospital_sevices.room_and_nursery.RoomFragment
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.nurseryCD.setOnClickListener { showFragment(RoomFragment(RoomType.Nursery)) }
        binding.emergencyRoomCD.setOnClickListener { showFragment(RoomFragment(RoomType.ICU)) }

        binding.bloodItemCD.setOnClickListener {
            showFragment(BloodBankFragment())
        }
    }

    private fun showFragment(fragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}