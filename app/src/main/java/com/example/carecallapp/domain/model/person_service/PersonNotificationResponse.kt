package com.example.carecallapp.domain.model.person_service

import java.io.Serializable


data class PersonNotificationResponse(

    val id: Int?,
    val date: String?,
    val patientName: String?,
    val quantity: String?,
    val patientId: String?,

    val latitude: Double?,
    val longitude: Double?,
    val targetLongitude: Double?,
    val targetLatitude: Double?,

    val phoneNumber: String?,
    val price: Int?,
    val patient: String?,
    val patientNationalId: String?,
    val caseDescription: String?,
    val status: RequestStatus?
):Serializable

enum class RequestStatus {
    Pending,
    Accepted,
    Rejected,
    Completed,
    Canceled,
    Confirmed
}