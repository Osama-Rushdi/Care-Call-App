package com.example.carecallapp.ui

import android.app.AlertDialog
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.drawToBitmap
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.carecallapp.R
import com.example.carecallapp.SettingsFeature
import com.example.carecallapp.databinding.ActivityMainBinding
import com.example.carecallapp.databinding.CustomDialogBinding
import com.example.carecallapp.domain.model.auth.Role
import com.example.carecallapp.domain.use_cases.UserSessionManager
import com.example.carecallapp.ui.utils.Constants
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var featureSharedPreferences: SharedPreferences
    private lateinit var isFirstSharedPreferences: SharedPreferences
    private var isFirst: Boolean=true
    private var json: String? = null
    private var token: String? = null
    private lateinit var locale: Locale

    companion object {
        val role = MutableLiveData(Role.Hospital.name)
        val updateRequestsCount = MutableLiveData(0)
    }

    @Inject
    lateinit var sessionManager: UserSessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsFeature()

        val direction =
            if (TextUtils.getLayoutDirectionFromLocale(locale) == View.LAYOUT_DIRECTION_RTL)
                View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
        window.decorView.layoutDirection = direction

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavController()
        getShredPre()
        showAndHideViewsByRole()
        initListenerOfNavigationDrawer()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            showNavDrawerInScreens(destination.id)
        }
        updateRequestsCountObserve()

    }

    private fun settingsFeature() {
        featureSharedPreferences =
            getSharedPreferences(Constants.SHARED_FEATURE_NAME, MODE_PRIVATE)
        json = featureSharedPreferences.getString(Constants.SHARED_FEATURE_KEY, null)
        if (!json.isNullOrEmpty()) {
            val settings = Gson().fromJson(json, SettingsFeature::class.java)

            AppCompatDelegate.setDefaultNightMode(
                if (settings.dark) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
            if (settings.arabic) {
                setAppLocale("ar")
            } else {
                setAppLocale("en")
            }
        }

    }

    private fun setAppLocale(language: String) {
        locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(
            config,
            resources.displayMetrics
        )
    }

    private fun showAndHideViewsByRole() {
        if (token == null && !isFirst) {
            navToLogin()
            return
        } else {
            navToWelcome()
        }
        roleObserver()
    }

    private fun navToWelcome() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.mobile_navigation, true)
            .setLaunchSingleTop(true)
            .build()

        navController.navigate(R.id.welcomeAppFragment, null, navOptions)
    }

    private fun roleObserver() {
        role.observe(this) {
            when (it) {
                Role.Hospital.name -> binding.navView.menu.findItem(R.id.manage_medical_services_iconMenu).isVisible =
                    true

                else -> {
                    binding.navView.menu.findItem(R.id.manage_medical_services_iconMenu).isVisible =
                        false
                }
            }
        }
    }

    private fun navToPersonNotification() {
        navController.navigate(R.id.global_to_person_notificationFragment)
    }

    private fun getShredPre() {
        sharedPreferences = getSharedPreferences(Constants.SHARED_TOKEN_NAME, MODE_PRIVATE)
        token = sharedPreferences.getString(Constants.SHARED_TOKEN_KEY, null)
        isFirstSharedPreferences = getSharedPreferences(Constants.IS_FIRST_NAME, MODE_PRIVATE)
        isFirst = isFirstSharedPreferences.getBoolean(Constants.IS_FIRST_KEY, true)
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
        binding.toolBar.notificationBtn.setOnClickListener {
            when (sessionManager.getUserRole()) {
                Role.Doctor.name -> {
                    navToPersonNotification()
                }

                Role.Ambulance.name -> {
                    navToPersonNotification()
                }

                Role.Hospital.name -> {
                    navToPersonNotification()
                }
            }
            navToPersonNotification()
        }
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home_iconMenu -> {
                    when (sessionManager.getUserRole()) {
                        Role.Doctor.name -> {
                            navController.navigate(R.id.doctorHomeFragment)

                        }

                        Role.Ambulance.name -> {
                            navController.navigate(R.id.ambulanceHomeFragment)

                        }

                        Role.Hospital.name -> {
                            navController.navigate(R.id.homeFragment)
                        }
                    }

                }

                R.id.manage_medical_services_iconMenu -> {
                    navController.navigate(R.id.medicalServicesFragment)
                }

                R.id.Log_out_iconMenu -> {
                    logoutDialog()
                }

                R.id.aboutUs_iconMenu -> {
                    navController.navigate(R.id.action_global_to_aboutUsFragment)
                }

                R.id.settings_iconMenu -> {
                    navController.navigate(R.id.settingsFragment)
                }
            }

            binding.drawerLayout.close()
            true
        }
        binding.navView.getHeaderView(0)
            .findViewById<ImageView>(R.id.profileImageView).apply {
                val image = findViewById<CircleImageView>(R.id.ivProfilePicture)
                if (image != null) {
                    setImageBitmap(image.drawToBitmap())
                }

                setOnClickListener {
                    when (sessionManager.getUserRole()) {
                        Role.Doctor.name -> {
                            navController.navigate(R.id.doctorProfileFragment)
                        }

                        Role.Ambulance.name -> {
                            navController.navigate(R.id.ambulanceProfileFragment)
                        }

                        Role.Hospital.name -> {
                            navController.navigate(R.id.hospitalProfileFragment)
                        }
                    }
                    binding.drawerLayout.close()
                }
            }
    }

    private fun navToLogin() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.mobile_navigation, true)
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
        val logoutIcon = binding.deleteButton
        logoutIcon.setOnClickListener {
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
        token = null

    }

    private fun showNavDrawerInScreens(id: Int) {
        when (id) {
            R.id.loginFragment, R.id.doctorRegisterFragment, R.id.ambulanceRegisterFragment,R.id.welcomeAppFragment -> {
                binding.toolBar.toolBarFile.isVisible = false
                binding.navView.isVisible = false
            }

            else -> {
                binding.navView.isVisible = true
                binding.toolBar.toolBarFile.isVisible = true
                binding.toolBar.toggleBtn.isVisible = true
            }
        }
    }

    private fun updateRequestsCountObserve() {
        updateRequestsCount.observe(this) {
            binding.toolBar.notificationBadgeCount.isVisible = it > 0
            if (updateRequestsCount.value != 0) {
                binding.toolBar.notificationBadgeCount.text = it.toString()
            } else {
                binding.toolBar.notificationBadgeCount.text = ""
            }
        }
    }

}