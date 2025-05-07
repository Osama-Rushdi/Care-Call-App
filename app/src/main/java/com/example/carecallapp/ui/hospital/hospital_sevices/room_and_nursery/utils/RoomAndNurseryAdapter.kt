package com.example.carecallapp.ui.hospital.hospital_sevices.room_and_nursery.utils

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.carecallapp.R
import com.example.carecallapp.databinding.EmergencyRoomItemBinding
import com.example.carecallapp.domain.model.hospital_content.RoomAndNursery
import com.example.carecallapp.domain.model.hospital_content.Status

class RoomAndNurseryAdapter(
    private var beds: List<RoomAndNursery?>?,
    val deleteItem: (id: Int) -> Unit,
    val editTask: (id: Int) -> Unit
) :
    Adapter<RoomAndNurseryAdapter.ViewHolder>() {
    class ViewHolder(val binding: EmergencyRoomItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            EmergencyRoomItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = beds!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bed = beds?.get(position)
        changeIcon(holder, position)
        initListener(holder, bed)
    }

    private fun initListener(
        holder: ViewHolder,
        bed: RoomAndNursery?
    ) {
        holder.binding.deleteRightView.setOnClickListener {
            deleteItem(bed!!.id)
            Log.d(
                "kkk",
                "onBindViewHolder: id :${
                    bed.id
                }"
            )
        }
        holder.binding.bloodItemCardView.setOnClickListener { editTask(bed!!.id) }
        holder.binding.root.setOnClickListener { return@setOnClickListener }
        holder.binding.editRoomImageView.setOnClickListener {
            editTask(
                bed!!.id,
            )
        }
    }

    private fun changeIcon(holder: ViewHolder, position: Int) {
        val bed = beds?.get(position)
        holder.binding.changerRoomNumberTV.text = bed!!.roomNumber
        holder.binding.changerStatusTV.text = bed.status.name
        Log.d("kkk", "changeIcon:  bed.status.name  ${bed.status.name}")
        Log.d("BedData", "RoomNumber: ${bed.roomNumber}, Status: ${bed.status}")

        if (holder.binding.changerStatusTV.text == Status.Available.name)
            holder.binding.changerStatusTV.setTextColor(holder.binding.root.context.getColor(R.color.green))
        else
            holder.binding.changerStatusTV.setTextColor(holder.binding.root.context.getColor(R.color.red))
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<RoomAndNursery?>?) {
        beds = newList
        notifyDataSetChanged()
    }
}