package com.example.carecallapp.ui.roles

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carecallapp.databinding.FragmentAboutUsBinding

class AboutUsFragment : Fragment() {
    lateinit var binding: FragmentAboutUsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse(
                    "mailto:osamaalsadany1@gmail.com" +
                            "?subject=" + Uri.encode("from Care Call App") +
                            "&body=" + Uri.encode("Hello Osama,\nI have a question regarding your app is that..\n")
                )
            }

            startActivity(intent)
        }

    }
}