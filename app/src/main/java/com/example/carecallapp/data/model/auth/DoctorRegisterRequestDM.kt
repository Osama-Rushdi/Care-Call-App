package com.example.carecallapp.data.model.auth

import com.google.gson.annotations.SerializedName
data class DoctorRegisterRequestDM(

	@field:SerializedName("lastName")
    var lastName: String? = null,

	@field:SerializedName("specialty")
	var specialty: String? = null,

	@field:SerializedName("gender")
	var gender: String? = null,

	@field:SerializedName("bio")
	var bio: String? = null,

	@field:SerializedName("dateOfBirth")
	var dateOfBirth: String? = null,

	@field:SerializedName("firstName")
	var firstName: String? = null,

	@field:SerializedName("password")
	var password: String? = null,

	@field:SerializedName("nationalId")
	var nationalId: String? = null,

	@field:SerializedName("phone")
	var phone: Int? = null,

	@field:SerializedName("hospitalId")
	var hospitalId: String? = null,

	@field:SerializedName("confirmPassword")
	var confirmPassword: String? = null,

	@field:SerializedName("licenseNumber")
	var licenseNumber: String? = null,

	@field:SerializedName("email")
	var email: String? = null,

	@field:SerializedName("username")
	var username: String? = null,

	@field:SerializedName("status")
	var status: String? = null
)