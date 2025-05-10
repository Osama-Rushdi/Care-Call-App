package com.example.carecallapp.domain.model.auth

data class AmbulanceRegisterResponse(
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val nationalId: String = "",
    val gender: String = "",
    val phone: Int = 0,
    val hospitalId: String = "",
    val confirmPassword: String = "",
    val vehicleNumber: String = "",
    val dateOfBirth: String = "",
    val email: String = "",
    val username: String = ""
)
