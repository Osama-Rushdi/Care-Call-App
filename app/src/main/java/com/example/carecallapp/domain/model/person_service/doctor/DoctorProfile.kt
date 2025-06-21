package com.example.carecallapp.domain.model.person_service.doctor

import java.io.Serializable

data class DoctorProfile(

	val lastName: String? = null,

	val specialty: String? = null,

	val gender: String? = null,

	val bio: String? = null,

	val dateOfBirth: String? = null,

	val profilePicture: String? = null,

	val firstName: String? = null,

	val nationalId: String? = null,

	val phone: Int? = null,

	val hospitalId: String? = null,

	val licenseNumber: String? = null,

	val email: String? = null,

	val username: String? = null,

	val status: String? = null
): Serializable
