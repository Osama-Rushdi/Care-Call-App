package com.example.carecallapp.domain.model.auth

import com.google.gson.annotations.SerializedName

data class DoctorRegisterRequest(

	val lastName: String? = null,

	val specialty: String? = null,

	val gender: String? = null,

	val bio: String? = null,

	val dateOfBirth: String? = null,
	val firstName: String? = null,
	val password: String? = null,

	@field:SerializedName("nationalId")
	val nationalId: String? = null,

	@field:SerializedName("phone")
	val phone: Int? = null,

	@field:SerializedName("hospitalId")
	val hospitalId: String? = null,

	@field:SerializedName("confirmPassword")
	val confirmPassword: String? = null,

	@field:SerializedName("licenseNumber")
	val licenseNumber: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)