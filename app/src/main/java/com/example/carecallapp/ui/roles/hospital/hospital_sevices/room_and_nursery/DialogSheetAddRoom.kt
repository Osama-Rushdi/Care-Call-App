package com.example.carecallapp.ui.roles.hospital.hospital_sevices.room_and_nursery


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.carecallapp.data.repository.view_models.MyRoomAndNurseryViewModel
import com.example.carecallapp.data.repository.view_models.RoomStateShow
import com.example.carecallapp.databinding.AddRoomDialogSheetBinding
import com.example.carecallapp.domain.model.hospital.hospital_content.RoomAndNursery
import com.example.carecallapp.domain.model.hospital.hospital_content.RoomStatus
import com.example.carecallapp.domain.model.hospital.hospital_content.RoomType
import com.example.carecallapp.ui.utils.ShowState
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogSheetAddRoom(val type: RoomType, private val reload: () -> Unit) :
    BottomSheetDialogFragment() {
    private lateinit var binding: AddRoomDialogSheetBinding
    private val viewModel: MyRoomAndNurseryViewModel by viewModels()
    lateinit var showState: ShowState


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddRoomDialogSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showState = ShowState(requireContext())
        initListener()
        viewModel.roomStateShow.observe(viewLifecycleOwner) {
            when (it) {


                is RoomStateShow.IsAddSuccess -> {
                    dismiss()
                    reload()
                }


                RoomStateShow.Loading -> showLoading(true)
                is RoomStateShow.ShowError -> {
                    showState.showError(it.errorMessage)
                    showLoading(false)
                }

                else -> {}
            }

        }
    }

    private fun initListener() {
        binding.addBloodButton.setOnClickListener {
            if (!isValid())
                return@setOnClickListener
            addBlood()
        }
    }

    private fun addBlood() {

        val room = RoomAndNursery(
            roomNumber = binding.roomNumberTextLayout.editText?.text.toString(),
            type = if (type == RoomType.ICU) RoomType.ICU else RoomType.Nursery,
            status = RoomStatus.Available
        )
        viewModel.addRoomAndNursery(room)
    }

    private fun showLoading(isVisible: Boolean) {
        binding.addLoading.root.isVisible = isVisible
    }


    private fun isValid(): Boolean {
        var isValid = true

        binding.roomNumberTextLayout.error =
            if (binding.roomNumberTextLayout.editText!!.text.trim().isEmpty()) {
                isValid = false
                "Enter Room Number"
            } else
                null

        return isValid
    }
}