package com.example.carecallapp.ui.hospital

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.carecallapp.R
import com.example.carecallapp.databinding.ActivityHospitalBinding
import com.example.carecallapp.ui.hospital.home.HomeFragment
import com.example.carecallapp.ui.hospital.medical_services.MedicalServicesFragment
import com.example.carecallapp.ui.hospital.profile.HospitalProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HospitalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHospitalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
        showFragment(HomeFragment())

    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun initListener() {
        binding.toolBar.toggleBtn.setOnClickListener {
            if (!binding.drawerLayout.isOpen)
                binding.drawerLayout.open()
        }
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home_iconMenu -> {
                    showFragment(HomeFragment())
                    true
                }

                R.id.manage_medical_services_iconMenu -> {
                    showFragment(MedicalServicesFragment())
                    true
                }

                else -> {
                    false
                }
            }
        }
        binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.profileImageView)
            .setOnClickListener {
                showFragment(HospitalProfileFragment())
            }
    }

    @Deprecated(
        "This method has been deprecated in favor of using the\n    " +
                "  {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n  " +
                "    The OnBackPressedDispatcher controls how back button events are dispatched\n   " +
                "   to one or more {@link OnBackPressedCallback} objects."
    )
    override fun onBackPressed() {
        if (binding.drawerLayout.isOpen) {
            binding.drawerLayout.close()
            return
        }
        super.onBackPressed()
        super.onBackPressedDispatcher.onBackPressed()
    }


}