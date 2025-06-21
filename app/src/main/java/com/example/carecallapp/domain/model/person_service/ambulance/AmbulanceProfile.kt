package com.example.carecallapp.domain.model.person_service.ambulance


data class AmbulanceProfile(

	val profilePicture: String? = "",

	val firstName: String? = "",

	val lastName: String? = "",

	val nationalId: String? = "",

	val gender: String? ,

	val phone: Int? = 0,

	val vehicleNumber: String? = "",

	val dateOfBirth: String? ,

	val email: String? ,

	val username: String? ,

	val status: String?
)
