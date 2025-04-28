package com.example.carecallapp.domain.model.hospital_profile

import java.io.Serializable

data class HospitalResponse(
	val profilePicture: String="",
	val firstName: String="",
	val lastName: String = "",
	val website: String = "",
	val nationalId: String = "",
	val gender: String = "",
	val phone: Int,
	val dateOfBirth: String = "",
	val email: String="",
	val username: String=""
):Serializable