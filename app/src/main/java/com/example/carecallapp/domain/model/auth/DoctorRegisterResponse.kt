package com.example.carecallapp.domain.model.auth

data class DoctorRegisterResponse(
    val lastName: String = "",
    val specialty: String = "",
    val gender: String = "",
    val bio: String = "",
    val dateOfBirth: String = "",
    val firstName: String = "",
    val password: String = "",
    val nationalId: String = "",
    val phone: Int = 0,
    val hospitalId: String = "",
    val confirmPassword: String = "",
    val licenseNumber: String = "",
    val email: String = "",
    val username: String = "",
    val status: String = ""
)
