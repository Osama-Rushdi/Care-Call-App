package com.example.carecallapp.ui.hospital.hospital_sevices.blood_bank.utils

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.carecallapp.databinding.BloodBankItemBinding
import com.example.carecallapp.domain.model.hospital_content.BloodBag

class BloodBankAdapter(
    private var bloodBags: List<BloodBag?>?,
    val deleteItem: (id: Int) -> Unit,
    val editTask: (id: Int, blood: BloodBag) -> Unit
) :
    Adapter<BloodBankAdapter.ViewHolder>() {
    class ViewHolder(val binding: BloodBankItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            BloodBankItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = bloodBags!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bloodBag = bloodBags?.get(position)
        changeIcon(holder, position)
        initListener(holder, bloodBag)
    }

    private fun initListener(
        holder: ViewHolder,
        bloodBag: BloodBag?
    ) {
        holder.binding.deleteRightView.setOnClickListener {

            deleteItem(bloodBag!!.id)
            Log.d(
                "kkk",
                "onBindViewHolder: id :${
                    bloodBag.id
                }"
            )
        }
        holder.binding.bloodItemCardView.setOnClickListener { editTask(bloodBag!!.id, bloodBag) }
        holder.binding.root.setOnClickListener { return@setOnClickListener }
        holder.binding.editBloodBagImageView.setOnClickListener {
            editTask(
                bloodBag!!.id,
                bloodBag
            )
        }
    }

    private fun changeIcon(holder: ViewHolder, position: Int) {
        val bags = bloodBags?.get(position)
        holder.binding.bloodType.text = bags?.bloodType
        holder.binding.bloodBagQuantity.text = bags?.bloodBagQuantity.toString()
        if (holder.binding.bloodBagQuantity.text.toString().trim()
                .toInt() > 10 || holder.binding.bloodBagQuantity.text.toString().trim().toInt() == 1
        )
            "Unit".let { holder.binding.unitTV.text = it }
        else
            "Units".let { holder.binding.unitTV.text = it }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<BloodBag?>?) {
        bloodBags = newList
        notifyDataSetChanged()
    }
}