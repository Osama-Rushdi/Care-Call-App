package com.example.carecallapp.data.model.PersonService

import com.google.gson.annotations.SerializedName

data class PersonNotificationResponseDM(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("patientName")
	val patientName: Any? = null,

	@field:SerializedName("quantity")
	val quantity: Any? = null,

	@field:SerializedName("patientId")
	val patientId: String? = null,

	@field:SerializedName("latitude")
	val latitude: Double? = null,

	@field:SerializedName("targetlongitude")
	val targetlongitude: Double? = null,

	@field:SerializedName("bloodType")
	val bloodType: Any? = null,

	@field:SerializedName("doctorName")
	val doctorName: Any? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: Any? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("targetlatitude")
	val targetlatitude: Double? = null,

	@field:SerializedName("patient")
	val patient: Any? = null,

	@field:SerializedName("service")
	val service: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("assignedEntityId")
	val assignedEntityId: String? = null,

	@field:SerializedName("serviceId")
	val serviceId: Int? = null,

	@field:SerializedName("patientNationalId")
	val patientNationalId: String? = null,

	@field:SerializedName("case")
	val jsonMemberCase: Any? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("longitude")
	val longitude: Double? = null)