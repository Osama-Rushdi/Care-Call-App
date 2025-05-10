package com.example.carecallapp.data.model.hospital_services

import com.google.gson.annotations.SerializedName

data class ServiceRequestDM(

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("serviceTypeId")
	val serviceTypeId: Int? = null
)