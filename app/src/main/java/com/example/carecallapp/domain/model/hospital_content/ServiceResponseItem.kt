package com.example.carecallapp.domain.model.hospital_content

data class ServiceResponseItem(
	val serviceType: Int? = null,
	val hospitalId: String,
	val price: Int,
	val name: String="",
	val serviceTypeId: Int? = null,
	val idAtList: Int ,
)
