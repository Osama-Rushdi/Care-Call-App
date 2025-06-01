package com.example.carecallapp.data.model.PersonService

import com.google.gson.annotations.SerializedName

data class LocationRequestDM(

	@field:SerializedName("latitude")
	val latitude: Double? = null,

	@field:SerializedName("longitude")
	val longitude: Double? = null
)
