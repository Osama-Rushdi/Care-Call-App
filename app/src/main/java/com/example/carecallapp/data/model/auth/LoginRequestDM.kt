package com.example.carecallapp.data.model.auth

import com.google.gson.annotations.SerializedName

data class LoginRequestDM(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)