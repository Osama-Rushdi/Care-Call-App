package com.example.carecallapp.data.model.auth
import com.google.gson.annotations.SerializedName

data class TokenResponseDM(

	@field:SerializedName("role")
	var role: String? = null,

	@field:SerializedName("expiration")
	val expiration: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("token")
	val token: String? = null

)