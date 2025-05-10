package com.example.carecallapp.data.model.hospital_services

import com.google.gson.annotations.SerializedName

data class ServiceResponseDM(

	@field:SerializedName("serviceType")
	val serviceType: String? = null,

	@field:SerializedName("clinicId")
	val clinicId: Any? = null,

	@field:SerializedName("hospitalId")
	val hospitalId: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("serviceTypeId")
	val serviceTypeId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("clinic")
	val clinic: Any? = null,

	@field:SerializedName("hospital")
	val hospital: Any? = null
)