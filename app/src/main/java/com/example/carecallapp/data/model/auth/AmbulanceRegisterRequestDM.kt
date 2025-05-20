package com.example.carecallapp.data.model.auth

import com.google.gson.annotations.SerializedName

data class AmbulanceRegisterRequestDM(

	@field:SerializedName("firstName")
	var firstName: String? = null,

	@field:SerializedName("lastName")
	var lastName: String? = null,

	@field:SerializedName("password")
	var password: String? = null,

	@field:SerializedName("nationalId")
	var nationalId: String? = null,

	@field:SerializedName("gender")
	var gender: String? = null,

	@field:SerializedName("phone")
	var phone: Int? = null,

	@field:SerializedName("hospitalId")
	var hospitalId: String? = null,

	@field:SerializedName("confirmPassword")
	var confirmPassword: String? = null,

	@field:SerializedName("vehicleNumber")
	var vehicleNumber: String? = null,

	@field:SerializedName("dateOfBirth")
	var dateOfBirth: String? = null,

	@field:SerializedName("email")
	var email: String? = null,

	@field:SerializedName("username")
	var username: String? = null
)