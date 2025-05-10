package com.example.carecallapp.domain.model.auth

data class HospitalRegisterResponse(
	val firstName: String = "",
	val lastName: String = "",
	val password: String = "",
	val website: String = "",
	val nationalId: String = "",
	val gender: String = "",
	val phone: Int = 0,
	val confirmPassword: String = "",
	val dateOfBirth: String = "",
	val email: String = "",
	val username: String = ""
)
