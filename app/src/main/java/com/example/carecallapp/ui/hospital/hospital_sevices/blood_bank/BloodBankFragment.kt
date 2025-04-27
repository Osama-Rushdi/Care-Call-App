package com.example.carecallapp.ui.hospital.hospital_sevices.blood_bank

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
import com.example.carecallapp.databinding.FragmentBloodBankBinding
import com.example.carecallapp.domain.model.hospital_content.BloodBag
import com.example.carecallapp.ui.hospital.hospital_sevices.blood_bank.utils.BloodBankAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BloodBankFragment : Fragment() {
    private lateinit var binding: FragmentBloodBankBinding
    private val viewModel: MyBloodBankViewModel by viewModels()
    private lateinit var adapter: BloodBankAdapter
    private var lastClickTime = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBloodBankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = BloodBankAdapter(emptyList(), {
            deleteItem(id)
            viewModel.getAllBloodBag() }) {id,blood->
            EditBloodDialogSheet(idBlood = id) {
                viewModel.getAllBloodBag()
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
                    showError(it.errorMessage)
                    showLoading(false)
                    notFound(false)

                }

                is BloodStateShow.IsBloodSuccess -> {}
                BloodStateShow.IsFound -> {
                    notFound(true)
                    showLoading(false)
                }
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
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.confirm))
            .setMessage(getString(R.string.are_you_confirm_to_delete_this_blood_bag))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                viewModel.deleteBloodBag(id)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showError(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun showLoading(isVisible: Boolean) {
        binding.bloodLoading.root.isVisible = isVisible
    }

    private fun notFound(enable: Boolean) {
        binding.notFoundBloods.root.isVisible = enable
    }
}