package com.example.carecallapp.ui.roles.hospital.hospital_sevices.blood_bank

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import com.example.carecallapp.R
import com.example.carecallapp.data.repository.view_models.BloodStateShow
import com.example.carecallapp.data.repository.view_models.MyBloodBankViewModel
import com.example.carecallapp.databinding.CustomDialogBinding
import com.example.carecallapp.databinding.FragmentBloodBankBinding
import com.example.carecallapp.ui.roles.hospital.hospital_sevices.blood_bank.utils.BloodBankAdapter
import com.example.carecallapp.ui.utils.ShowState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BloodBankFragment : Fragment() {
    private lateinit var binding: FragmentBloodBankBinding
    private val viewModel: MyBloodBankViewModel by viewModels()
    private lateinit var adapter: BloodBankAdapter
    private var lastClickTime = 0L
    lateinit var showState: ShowState

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBloodBankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showState = ShowState(requireContext())
        adapter = BloodBankAdapter(emptyList(), { id ->
            safeDelete(id)
        }) { id, _ ->
            EditBloodDialogSheet(idBlood = id) {
            }.show(parentFragmentManager, null)
        }
        binding.bloodBankRecyclerView.adapter = adapter
        viewModel.getAllBloodBag()
        viewModel.initBloodAdapter.observe(viewLifecycleOwner) { bags ->
            adapter.submitList(bags)
        }
        viewModel.bloodStateShow.observe(viewLifecycleOwner) {
            when (it) {
                is BloodStateShow.IsSuccess -> {
                    viewModel.initBloodAdapter.value = it.bags
                    showLoading(false)
                    notFound(false)
                }

                BloodStateShow.Loading -> {
                    showLoading(true)
                    notFound(false)
                }

                is BloodStateShow.ShowError -> {
                    showState.showError(it.errorMessage)
                    showLoading(false)
                    notFound(false)

                }

                BloodStateShow.IsFound -> {
                    notFound(true)
                    showLoading(false)
                }

                is BloodStateShow.IsDeleteSuccess -> {
                    //  viewModel.getAllBloodBag()
                }

                else -> {}
            }
        }
        binding.fabAddBloodBtn.setOnClickListener {
            DialogSheetAddBlood() {
                viewModel.getAllBloodBag()
            }.show(parentFragmentManager, null)
        }
    }

    private fun safeDelete(id: Int) {
        if (System.currentTimeMillis() - lastClickTime < 1000) Toast.makeText(
            requireContext(),
            "wait 1sc and try again",
            Toast.LENGTH_SHORT
        ).show()
        lastClickTime = System.currentTimeMillis()
        deleteItem(id)
    }

    private fun deleteItem(id: Int) {
        val binding = CustomDialogBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(binding.root)
        val dialog = dialogBuilder.create()

        binding.massageTV.text = getString(R.string.are_you_want_to_delete_this_item)
        binding.deleteButton.setOnClickListener {
            viewModel.deleteBloodBag(id)
            dialog.dismiss()
        }
        binding.cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun showLoading(isVisible: Boolean) {
        binding.bloodLoading.root.isVisible = isVisible
    }

    private fun notFound(enable: Boolean) {
        binding.notFoundBloods.root.isVisible = enable
    }
}