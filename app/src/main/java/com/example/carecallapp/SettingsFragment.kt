package com.example.carecallapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.LocaleList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.carecallapp.databinding.FragmentSettingsBinding
import com.example.carecallapp.ui.utils.Constants
import com.google.gson.Gson
import java.util.Locale

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var settingsFeature: SettingsFeature
    private lateinit var sharedPreferences: SharedPreferences
    private val TAG = "SettingsFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(
            Constants.SHARED_FEATURE_NAME,
            Context.MODE_PRIVATE
        )
        binding.switchDarkMode.isChecked =
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        val currentLocale = Locale.getDefault().language
        if (currentLocale == "ar") {
            binding.spinnerLanguage.setSelection(0) // Arabic
        } else {
            binding.spinnerLanguage.setSelection(1) // English
        }

        initListener()
    }

    private fun initListener() {

        binding.btnSave.setOnClickListener {

            settingsFeature = SettingsFeature(
                binding.switchDarkMode.isChecked,
                binding.switchNotifications.isChecked,
                binding.spinnerLanguage.selectedItemPosition == 0
            )
            //check if user change any buttons
            if (Gson().fromJson(
                    sharedPreferences.getString(Constants.SHARED_FEATURE_KEY, null),
                    SettingsFeature::class.java
                ) == settingsFeature
            )
                return@setOnClickListener

            saveSettings(settingsFeature)
            applySettings(settingsFeature)
            requireActivity().recreate()
        }
    }

    private fun saveSettings(settings: SettingsFeature) {
        val gson = Gson().toJson(settings)
        Log.d(TAG, "saveSettings: $gson")
        sharedPreferences.edit()
            .putString(Constants.SHARED_FEATURE_KEY, gson)
            .apply()
    }

    private fun applySettings(settings: SettingsFeature) {
        AppCompatDelegate.setDefaultNightMode(
            if (settings.dark)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO
        )

        if (settings.arabic) {
            setLanguage("ar")
        } else {
            setLanguage("en")
        }
    }

    private fun setLanguage(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = requireContext().resources.configuration
        config.setLocale(locale)
        requireContext().resources.updateConfiguration(
            config,
            requireContext().resources.displayMetrics
        )

    }
}

data class SettingsFeature(
    val dark: Boolean = false,
    val hideNotification: Boolean = false,
    val arabic: Boolean = false
)