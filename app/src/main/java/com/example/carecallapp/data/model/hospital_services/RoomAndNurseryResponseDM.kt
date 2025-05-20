package com.example.carecallapp.data.model.hospital_services

import com.google.gson.annotations.SerializedName



data class RoomAndNurseryResponseDM(

	@field:SerializedName("hospitalId")
	val hospitalId: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("bedNumber")
	val bedNumber: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("hospital")
	val hospital: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
