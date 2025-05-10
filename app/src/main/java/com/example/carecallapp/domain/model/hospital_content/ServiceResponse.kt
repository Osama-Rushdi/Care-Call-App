package com.example.carecallapp.domain.model.hospital_content


data class ServiceResponse(

    val serviceType: String,
    val price: Int = 0,
    val name: String = "",
    val description: String = "",
    val serviceTypeId: Int,
    val id: Int,
)

enum class ServiceType {
    Ambulance, Doctor, Nursery,BloodBank,ICU
}