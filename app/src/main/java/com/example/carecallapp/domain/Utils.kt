package com.example.carecallapp.domain

import com.example.carecallapp.domain.model.hospital.hospital_content.ServiceType



object Types{
    fun peopleTypeUrl(ifDoctor:Boolean):String=if (ifDoctor) "api/Doctor/GetDoctorsOfHospital" else "api/Ambulance/GetAmbulancesOfHospital"
}
object ServiceTypeUtil {
    private val serviceTypeMap = mapOf(
        ServiceType.Ambulance.name to 1,
        ServiceType.BloodBank.name to 2,
        ServiceType.ICU.name to 3,
        ServiceType.Doctor.name to 4,
        ServiceType.Nursery.name to 5
    )

    fun getId(name: String): Int = serviceTypeMap[name]
        ?: throw IllegalArgumentException("Invalid service name: $name")
}
