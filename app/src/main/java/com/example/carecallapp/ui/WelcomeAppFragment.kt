package com.example.carecallapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.carecallapp.R
import com.example.carecallapp.databinding.FragmentWelcomeAppBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeAppFragment : Fragment() {
    lateinit var binding: FragmentWelcomeAppBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentWelcomeAppBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        checkFirstTimeUser()
    }

    private fun setupViews() {
        binding.getStartedButton.setOnClickListener {
            navToLogin()
        }


//        binding.skipText.setOnClickListener {
//            navToLogin()
//        }


        binding.animationView.addAnimatorUpdateListener {
            val progress = it.animatedValue as Float
            binding.title.scaleX = 1 + (progress * 0.1f)
            binding.title.scaleY = 1 + (progress * 0.1f)
        }
    }

    private fun navToLogin() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.mobile_navigation, true)
            .setLaunchSingleTop(true)
            .build()

        val action = WelcomeAppFragmentDirections.actionWelcomeAppFragmentToLoginFragment()
        findNavController().navigate(action, navOptions)
    }

    private fun checkFirstTimeUser() {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val isFirstTime = sharedPref.getBoolean("is_first_time", true)

        when {
            !isFirstTime -> {
            }
            else -> {
                sharedPref.edit().putBoolean("is_first_time", false).apply()
            }
        }
    }

}