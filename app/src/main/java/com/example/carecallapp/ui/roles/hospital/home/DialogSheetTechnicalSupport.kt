package com.example.carecallapp.ui.roles.hospital.home


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.carecallapp.databinding.FragmentTechnicalSupportBinding
import com.example.carecallapp.ui.utils.submitToGoogleForm
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


@AndroidEntryPoint
class DialogSheetTechnicalSupport :
    DialogFragment() {
    private lateinit var binding: FragmentTechnicalSupportBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTechnicalSupportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        isCancelable = false

    }

    private fun initListener() {

        binding.btnSend.setOnClickListener {
            val name = binding.inputName.text.toString().trim()
            val message = binding.inputMessage.text.toString().trim()

            if (isValid()) {
                submitToGoogleForm(name, message)
                showSuccess()
                dismiss()
            }
        }
        binding.closeButton.setOnClickListener { dismiss() }
    }

    private fun showSuccess() {
        if (isValid()) {
            AlertDialog.Builder(requireContext())
                .setTitle("Success")
                .setMessage("Your request has been sent successfully.")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }


    private fun isValid(): Boolean {
        var isValid = true
        binding.textInputLayoutName.error =
            if (binding.textInputLayoutName.editText?.text.toString().isBlank()) {
                isValid = false
                "Enter User Name"
            } else
                null

        binding.textInputLayoutMessage.error =
            if (binding.textInputLayoutMessage.editText?.text!!.trim().isEmpty()) {
                isValid = false
                "Enter Message"
            } else
                null

        return isValid
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.90).toInt(),
            (resources.displayMetrics.heightPixels * 0.80).toInt()
        )
    }

}