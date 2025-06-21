package com.example.carecallapp.ui.roles.hospital.hospital_sevices.blood_bank


//noinspection SuspiciousImport
import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.carecallapp.data.repository.view_models.BloodStateShow
import com.example.carecallapp.data.repository.view_models.MyBloodBankViewModel
import com.example.carecallapp.databinding.AddBloodDialogSheetBinding
import com.example.carecallapp.domain.model.hospital.hospital_content.BloodBag
import com.example.carecallapp.ui.utils.ShowState
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogSheetAddBlood(private val reload: () -> Unit) :
    BottomSheetDialogFragment() {
    private lateinit var binding: AddBloodDialogSheetBinding
    private val viewModel: MyBloodBankViewModel by viewModels()
    lateinit var showState: ShowState

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddBloodDialogSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showState = ShowState(requireContext())
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
                    showState.showError(it.errorMessage)
                    showLoading(false)
                }

                BloodStateShow.IsFound -> {}
                is BloodStateShow.IsAddSuccess -> {
                    dismiss()
                    reload()
                }

                else -> {}
            }
        }
    }


    private fun initListener() {
        binding.addBloodButton.setOnClickListener {
            if (!isValid())
                return@setOnClickListener
            editBlood()
        }
    }

    private fun editBlood() {
        val blood = BloodBag(
            bloodType = binding.bloodTypeDropDown.text.toString(),
            bloodBagQuantity = binding.bloodDescriptionTextLayout.editText!!.text.toString()
                .toInt(),
        )
        viewModel.addBloodBag(blood)
    }

    private fun showLoading(isVisible: Boolean) {
        binding.addLoading.root.isVisible = isVisible
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