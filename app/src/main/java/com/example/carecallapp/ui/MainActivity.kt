package com.example.carecallapp.ui

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.example.carecallapp.R
import com.example.carecallapp.databinding.ActivityMainBinding
import com.example.carecallapp.databinding.CustomDialogBinding
import com.example.carecallapp.ui.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences
    private var token: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavController()
        getToken()
        initListenerOfNavigationDrawer()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            showNavDrawerInScreens(destination.id)
        }
    }

    private fun navToNotification() {
        navController.navigate(R.id.global_to_notificationFragment)
    }

    private fun getToken() {
        sharedPreferences = getSharedPreferences(Constants.SHARED_TOKEN_NAME, MODE_PRIVATE)
        token = sharedPreferences.getString(Constants.SHARED_TOKEN_KEY, null)
    }

    private fun initNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun initListenerOfNavigationDrawer() {
        binding.toolBar.toggleBtn.setOnClickListener {
            if (!binding.drawerLayout.isOpen) binding.drawerLayout.open()
        }
        binding.toolBar.hospitalNotificationBtn.setOnClickListener {
          navToNotification()
        }
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home_iconMenu -> {
                    navController.navigate(R.id.homeFragment)
                }

                R.id.manage_medical_services_iconMenu -> {
                    navController.navigate(R.id.medicalServicesFragment)
                }

                R.id.Log_out_iconMenu -> logoutDialog()
            }

            binding.drawerLayout.close()
            true
        }

        binding.navView.getHeaderView(0)
            .findViewById<ImageView>(R.id.profileImageView)
            .setOnClickListener {
                navController.navigate(R.id.hospitalProfileFragment)
                binding.drawerLayout.close()
            } }

    private fun navToLogin() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.mobile_navigation, true) // بيمسح كل اللي فات
            .setLaunchSingleTop(true)
            .build()

        navController.navigate(R.id.loginFragment, null, navOptions)
    }

    private fun logoutDialog() {
        val binding = CustomDialogBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setView(binding.root)
        val dialog = dialogBuilder.create()

        binding.massageTV.text = getString(R.string.can_you_confirm_logout)
        binding.deleteButton.text = getString(R.string.log_out)
        binding.deleteButton.setOnClickListener {
            deleteToken()
            navToLogin()
            dialog.dismiss()
        }
        binding.cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun deleteToken() {
        sharedPreferences.edit().clear().apply()
           token=null
    }

    private fun showNavDrawerInScreens(id:Int) {
        when (id) {
            R.id.loginFragment, R.id.doctorRegisterFragment, R.id.ambulanceRegisterFragment -> {
                binding.toolBar.toolBarFile.isVisible = false
                binding.navView.isVisible = false
            }
            else -> {
                binding.navView.isVisible = true
                binding.toolBar.toolBarFile.isVisible = true
                binding.toolBar.toggleBtn.isVisible=true
            }
        }
    }

    fun updateNotificationBadge(count: Int) {
    if (count != 0) {
        binding.toolBar.notificationBadgeCount.text = count.toString()
    }
else {
        binding.toolBar.notificationBadgeCount.text = ""
    }
    binding.toolBar.notificationBadgeCount.isVisible = count > 0

}
}