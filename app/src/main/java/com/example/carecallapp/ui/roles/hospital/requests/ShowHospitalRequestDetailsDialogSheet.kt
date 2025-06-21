package com.example.carecallapp.ui.roles.hospital.requests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.example.carecallapp.databinding.FragmentShowHospitalRequestDetailsDialogSheetBinding
import com.example.carecallapp.domain.model.hospital.hospital_notification.HospitalNotificationResponse
import com.example.carecallapp.ui.utils.formatDateToRequests

class ShowHospitalRequestDetailsDialogSheet :  DialogFragment() {
    lateinit var request: HospitalNotificationResponse
    lateinit var binding: FragmentShowHospitalRequestDetailsDialogSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowHospitalRequestDetailsDialogSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: ShowHospitalRequestDetailsDialogSheetArgs by navArgs()
        request = args.request

        binding.apply {
            tvPatientName.text = request.patientName ?: "Unknown"
            tvDate.text = formatDateToRequests(request.date ?:"-")
            tvQuantity.text = request.quantity ?: "-"
            tvPhoneNumber.text = request.phoneNumber ?: "-"
           "${request.price?.toString() ?: "-"} EGP".let {   tvPrice.text = it}
            tvRequestId.text = request.id.toString()
            tvCaseDescription.text = request.caseDescription ?: "-"
            tvServiceStatus.text = request.status?.name ?: "Unknown"
            tvBloodType.text = request.bloodType ?: "-"
            btnClose.setOnClickListener { dismiss() }
            binding.tvServiceType.text=request.service!!.name
        }

    }

}