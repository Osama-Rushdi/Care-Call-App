package com.example.carecallapp.domain.model.hospital.hospital_content

data class ServiceRequest(

    val price: Int = 0,

    val name: String = "",

    val description: String = "",

    val serviceTypeId: Int

)