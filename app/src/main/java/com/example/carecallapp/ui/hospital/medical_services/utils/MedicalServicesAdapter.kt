package com.example.carecallapp.ui.hospital.medical_services.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.carecallapp.R
import com.example.carecallapp.databinding.MedicalServicesItemBinding
import com.example.carecallapp.domain.model.hospital_accounts.PersonService

class MedicalServicesAdapter(
    private var accounts: List<PersonService?>?,
    private val callSelectIfDoctor: () -> Boolean
) :
    Adapter<MedicalServicesAdapter.ViewHolder>() {
    class ViewHolder(val binding: MedicalServicesItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MedicalServicesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = accounts!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        changeIcon(holder, position)
    }

    private fun changeIcon(holder: ViewHolder, position: Int) {
        val account = accounts?.get(position)

        if (callSelectIfDoctor()) {
            holder.binding.iconAccountItem.setImageResource(R.drawable.ic_doctor)
            holder.binding.accountNameTv.text = account?.userName

        } else {
            holder.binding.iconAccountItem.setImageResource(R.drawable.ic_ambulance)
            holder.binding.accountNameTv.text = account?.userName
        }
    }

}