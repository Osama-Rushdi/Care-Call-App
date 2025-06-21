package com.example.carecallapp.ui.roles.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carecallapp.databinding.FragmentShowPersonRequestDetailsDialogSheetBinding
import com.example.carecallapp.domain.model.person_service.PersonNotificationResponse
import com.example.carecallapp.ui.utils.formatDateToRequests

class ShowPersonRequestDetailsDialogSheet : DialogFragment() {
    lateinit var request: PersonNotificationResponse
    lateinit var binding: FragmentShowPersonRequestDetailsDialogSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentShowPersonRequestDetailsDialogSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: ShowPersonRequestDetailsDialogSheetArgs by navArgs()
        request = args.request

        binding.apply {
            tvPatientName.text = request.patientName ?: "Unknown"
            tvDate.text = formatDateToRequests(request.date ?: "-")
            tvQuantity.text = request.quantity ?: "-"
            tvPhoneNumber.text = request.phoneNumber ?: "-"
            "${request.price?.toString() ?: "-"} EGP".let { tvPrice.text = it }
            tvRequestId.text = request.id.toString()
            tvCaseDescription.text = request.caseDescription ?: "-"
            tvServiceStatus.text = request.status?.name ?: "Unknown"
            btnClose.setOnClickListener { dismiss() }
            navToMap.setOnClickListener { navToLocation(29.148088,31.1388966)
            //todo: changed it by real location
            }
        }

    }

    private fun navToLocation(latitude: Double?, longitude: Double?) {
        val action =
            ShowPersonRequestDetailsDialogSheetDirections.actionShowPersonRequestDetailsDialogSheetToMapFragment(
                longitude!!.toFloat(),
                latitude!!.toFloat()
            )
        findNavController().navigate(action)
    }

}