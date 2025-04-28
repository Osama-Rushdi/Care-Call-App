package com.example.carecallapp.data.model.hospital_services

import com.google.gson.annotations.SerializedName

data class BloodBagDM(
	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("hospitalId")
	val hospitalId: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("bloodType")
	val bloodType: String? = null,

	@field:SerializedName("hospital")
	val hospital: Any? = null
)