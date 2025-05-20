package com.example.carecallapp.ui

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.carecallapp.R
import com.example.carecallapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
         navController = navHostFragment.navController
        val token = getSharedPreferences("my_prefs", Context.MODE_PRIVATE).getString("token", null)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (token != null && destination.id == R.id.loginFragment) {
                navController.navigate(R.id.homeFragment)
            }
        }
        setupNavigationDrawer()
    }

    private fun setupNavigationDrawer() {
        binding.toolBar.toggleBtn.setOnClickListener {
            if (!binding.drawerLayout.isOpen) binding.drawerLayout.open()
        }

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home_iconMenu -> {
                    navController.navigate(R.id.homeFragment)
                }
                R.id.manage_medical_services_iconMenu -> {
                    navController.navigate(R.id.medicalServicesFragment)
                }
            }
            binding.drawerLayout.close()
            true
        }

        binding.navView.getHeaderView(0)
            .findViewById<ImageView>(R.id.profileImageView)
            .setOnClickListener {
                navController.navigate(R.id.hospitalProfileFragment)
                binding.drawerLayout.close()
            }
    }

}