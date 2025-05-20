package com.example.carecallapp.domain.model.auth

data class AmbulanceRegisterRequest(
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val nationalId: String = "",
    val gender: String = "",
    val phone: Int = 0,
    val hospitalId: String = "",
    val confirmPassword: String = "",
    var vehicleNumber: String = "",
    val dateOfBirth: String = "",
    val email: String = "",
    val username: String = ""
)
