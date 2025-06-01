package com.example.carecallapp.ui.hospital.hospital_sevices.blood_bank


import android.R
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.carecallapp.data.repository.view_models.BloodStateShow
import com.example.carecallapp.data.repository.view_models.MyBloodBankViewModel
import com.example.carecallapp.databinding.EditBloodDialogSheetBinding
import com.example.carecallapp.domain.model.hospital.hospital_content.BloodBag
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditBloodDialogSheet( private val idBlood:Int,private val reload: () -> Unit) :
    BottomSheetDialogFragment() {
    private lateinit var binding: EditBloodDialogSheetBinding
    private val viewModel: MyBloodBankViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditBloodDialogSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        val bloodTypes = listOf(
            "APositive", "ANegative",
            "BPositive", "BNegative",
            "ABPositive", "ABNegative",
            "OPositive", "ONegative"
        )
        val adapter =
            ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, bloodTypes)
        binding.bloodTypeDropDown.setAdapter(adapter)
        viewModel.bloodStateShow.observe(viewLifecycleOwner) {
            when (it) {
                is BloodStateShow.IsSuccess -> {}

                BloodStateShow.Loading -> {
                    showLoading(true)
                }

                is BloodStateShow.ShowError -> {
                    showError(it.errorMessage)
                    showLoading(false)
                }

                is BloodStateShow.IsUpdateSuccess -> {
                    dismiss()
                    reload()
                }
                else->{}
            }
        }
    }


    private fun initListener() {
        binding.editBloodButton.setOnClickListener {
            if (!isValid())
                return@setOnClickListener
            editBlood()
        }
    }

    private fun editBlood() {
        val blood = BloodBag(
          bloodType =   binding.bloodTypeDropDown.text.toString(),
          bloodBagQuantity =   binding.bloodDescriptionTextLayout.editText!!.text.toString().toInt(),
        )
        viewModel.updateBloodBag(idBlood,blood)
    }

    private fun showLoading(isVisible: Boolean) {
        binding.addLoading.root.isVisible = isVisible
    }

    private fun showError(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun isValid(): Boolean {
        var isValid = true
        binding.bloodTypeDropDown.error =
            if (binding.bloodTypeDropDown.text.toString().isBlank()) {
                isValid = false
                "select type"
            } else
                null

        binding.bloodDescriptionTextLayout.error =
            if (binding.bloodDescriptionTextLayout.editText!!.text.trim().isEmpty()) {
                isValid = false
                "Enter Description"
            } else
                null

        return isValid
    }
}