package com.example.carecallapp.ui.roles.hospital.hospital_sevices.room_and_nursery

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.carecallapp.R
import com.example.carecallapp.data.repository.view_models.MyRoomAndNurseryViewModel
import com.example.carecallapp.data.repository.view_models.RoomStateShow
import com.example.carecallapp.databinding.CustomDialogBinding
import com.example.carecallapp.databinding.FragmentRoomBinding
import com.example.carecallapp.domain.model.hospital.hospital_content.RoomType
import com.example.carecallapp.ui.roles.hospital.hospital_sevices.room_and_nursery.utils.RoomAndNurseryAdapter
import com.example.carecallapp.ui.utils.ShowState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomFragment : Fragment() {
    private lateinit var binding: FragmentRoomBinding
    private val viewModel: MyRoomAndNurseryViewModel by viewModels()
    private lateinit var adapter: RoomAndNurseryAdapter
    private var lastClickTime = 0L
    private lateinit var roomType : RoomType
    lateinit var showState: ShowState

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showState = ShowState(requireContext())
        val args: RoomFragmentArgs by navArgs()
        roomType = args.roomType

        changeHeaderText()
        initAdapter()
        getAllRoomAndNursery()
        observeData()
        initListener()

    }


    private fun initListener() {
        binding.fabAddRoomBtn.setOnClickListener {
            DialogSheetAddRoom(roomType) {
                getAllRoomAndNursery()
            }.show(parentFragmentManager, null)
        }
    }

    private fun observeData() {
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
                    showState.showError(it.errorMessage)
                    showLoading(false)
                    notFound(false)
                }

                RoomStateShow.IsFound -> {
                    notFound(true)
                    showLoading(false)
                }

                is RoomStateShow.IsDeleteSuccess -> {
                    Toast.makeText(requireContext(), "is delete successful", Toast.LENGTH_SHORT)
                        .show()
                    getAllRoomAndNursery()
                    showLoading(false)
                    notFound(false)
                }

                else -> {}
            }
        }
    }

    private fun initAdapter() {
        adapter = RoomAndNurseryAdapter(emptyList(), { id ->
            deleteItem(id)
            getAllRoomAndNursery()
            Log.d("kkk", "onViewCreated:$id ")
        }) { id ->
            EditRoomDialogSheet(roomType, idBlood = id) {
            }//.show(parentFragmentManager, null)
        }
        binding.EmergencyRoomRecyclerView.adapter = adapter
    }

    private fun changeHeaderText() {
        if (roomType == RoomType.ICU)
            "Emergency Room".let { binding.roomAndNurseryTV.text = it }
        else
            "Nursery Room".let { binding.roomAndNurseryTV.text = it }
    }

    private fun deleteItem(id: Int) {
        val binding = CustomDialogBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(binding.root)
        val dialog = dialogBuilder.create()

        binding.massageTV.text = getString(R.string.are_you_want_to_delete_this_item)
        binding.deleteButton.setOnClickListener {
            viewModel.deleteRoomOrNursery(id)
            dialog.dismiss()
        }
        binding.cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun getAllRoomAndNursery() {
        if (roomType == RoomType.ICU)
            viewModel.getAllRooms()
        else
            viewModel.getAllNurseries()
    }


    private fun showLoading(isVisible: Boolean) {
        binding.EmergencyRoomLoading.root.isVisible = isVisible
    }

    private fun notFound(enable: Boolean) {
        binding.notFoundBloods.root.isVisible = enable
    }


}

