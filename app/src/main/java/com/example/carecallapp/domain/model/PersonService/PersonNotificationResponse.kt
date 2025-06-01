package com.example.carecallapp.domain.model.PersonService


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
    )
enum class RequestStatus {
    Pending,
    Accepted,
    Rejected,
    Completed,
    Canceled,
    Confirmed
}