package com.example.carecallapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.carecallapp.databinding.ActivityMainBinding
import com.example.carecallapp.ui.home.HomeFragment
import com.example.carecallapp.ui.medical_services.MedicalServicesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isOpen) {
            binding.drawerLayout.close()
            return
        }
        super.onBackPressed()
    }
}