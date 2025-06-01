package com.example.carecallapp.data.model.hospital.hospital_profileDM

import com.google.gson.annotations.SerializedName

data class HospitalResponseDM(

	@field:SerializedName("profilePicture")
	val profilePicture: String? = null,

	@field:SerializedName("firstName")
	val firstName: String? = null,

	@field:SerializedName("lastName")
	val lastName: String? = null,

	@field:SerializedName("website")
	val website: String? = null,

	@field:SerializedName("nationalId")
	val nationalId: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("phone")
	val phone: Int? = null,

	@field:SerializedName("dateOfBirth")
	val dateOfBirth: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)