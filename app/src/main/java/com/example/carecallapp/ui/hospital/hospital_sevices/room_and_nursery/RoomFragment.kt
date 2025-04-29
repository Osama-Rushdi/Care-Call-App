package com.example.carecallapp.ui.hospital.hospital_sevices.room_and_nursery

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import com.example.carecallapp.R
import com.example.carecallapp.data.repository.view_models.MyRoomAndNurseryViewModel
import com.example.carecallapp.data.repository.view_models.RoomStateShow
import com.example.carecallapp.databinding.FragmentEmergencyRoomBinding
import com.example.carecallapp.domain.model.hospital_content.RoomType
import com.example.carecallapp.ui.hospital.hospital_sevices.room_and_nursery.utils.RoomAndNurseryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomFragment(val type: RoomType) : Fragment() {
    private lateinit var binding: FragmentEmergencyRoomBinding
    private val viewModel: MyRoomAndNurseryViewModel by viewModels()
    private lateinit var adapter: RoomAndNurseryAdapter
    private var lastClickTime = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmergencyRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ChangeHeaderText()

        adapter = RoomAndNurseryAdapter(emptyList(), { id ->
            deleteItem(id)
            Log.d("kkk", "onViewCreated:$id ")
        }) { id ->
            EditRoomDialogSheet(idBlood = id) {
            }.show(parentFragmentManager, null)
        }
        binding.EmergencyRoomRecyclerView.adapter = adapter
        getAllRoomAndNursery()
        viewModel.initRoomAdapter.observe(viewLifecycleOwner) { beds ->
            adapter.submitList(beds)
        }
        viewModel.roomStateShow.observe(viewLifecycleOwner) {
            when (it) {
                is RoomStateShow.IsSuccess -> {
                    viewModel.initRoomAdapter.value = it.rooms
                    showLoading(false)
                    notFound(false)
                }

                RoomStateShow.Loading -> {
                    showLoading(true)
                    notFound(false)
                }

                is RoomStateShow.ShowError -> {
                    showError(it.errorMessage)
                    showLoading(false)
                    notFound(false)

                }

                RoomStateShow.IsFound -> {
                    notFound(true)
                    showLoading(false)
                }

                is RoomStateShow.IsDeleteSuccess -> {
                    showLoading(false)
                    notFound(false)
                }

                else -> {}
            }
        }
        binding.fabAddRoomBtn.setOnClickListener {
            DialogSheetAddRoom(type) {
                getAllRoomAndNursery()
            }.show(parentFragmentManager, null)
        }
    }

    private fun ChangeHeaderText() {
        if (type == RoomType.ICU)
            "Emergency Room".let { binding.roomAndNurseryTV.text = it }
        else
            "Nursery Room".let { binding.roomAndNurseryTV.text = it }
    }

    private fun safeDelete(id: Int) {
        if (System.currentTimeMillis() - lastClickTime < 1000) Toast.makeText(
            requireContext(), "wait 1sc and try again", Toast.LENGTH_SHORT
        ).show()
        lastClickTime = System.currentTimeMillis()
        deleteItem(id)
    }

    private fun deleteItem(id: Int) {
        AlertDialog.Builder(requireContext()).setTitle(getString(R.string.confirm))
            .setMessage((getString(R.string.are_you_confirm_to_delete_this_room)))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                viewModel.deleteRoomOrNursery(id)
                getAllRoomAndNursery()
            }.setNegativeButton(getString(R.string.cancel), null).show()
    }

    private fun getAllRoomAndNursery() {
        if (type == RoomType.ICU)
            viewModel.getAllRooms()
        else
            viewModel.getAllNurseries()
    }

    private fun showError(message: String) {
        AlertDialog.Builder(requireContext()).setTitle("Error").setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }.show()
    }

    private fun showLoading(isVisible: Boolean) {
        binding.EmergencyRoomLoading.root.isVisible = isVisible
    }

    private fun notFound(enable: Boolean) {
        binding.notFoundBloods.root.isVisible = enable
    }
}