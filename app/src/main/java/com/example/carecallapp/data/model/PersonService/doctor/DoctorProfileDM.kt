package com.example.carecallapp.data.model.PersonService.doctor

import com.google.gson.annotations.SerializedName

data class DoctorProfileDM(

	@field:SerializedName("lastName")
	val lastName: String? = null,

	@field:SerializedName("specialty")
	val specialty: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("dateOfBirth")
	val dateOfBirth: String? = null,

	@field:SerializedName("profilePicture")
	val profilePicture: String? = null,

	@field:SerializedName("firstName")
	val firstName: String? = null,

	@field:SerializedName("nationalId")
	val nationalId: String? = null,

	@field:SerializedName("phone")
	val phone: Int? = null,

	@field:SerializedName("hospitalId")
	val hospitalId: String? = null,

	@field:SerializedName("licenseNumber")
	val licenseNumber: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
