package com.example.carecallapp.data.mappers

import com.example.carecallapp.data.model.PersonService.LocationRequestDM
import com.example.carecallapp.data.model.PersonService.MapRouteResponseDM
import com.example.carecallapp.data.model.PersonService.PersonNotificationResponseDM
import com.example.carecallapp.data.model.PersonService.ambulance.AmbulanceProfileDM
import com.example.carecallapp.data.model.PersonService.doctor.DoctorProfileDM
import com.example.carecallapp.data.model.auth.AmbulanceRegisterRequestDM
import com.example.carecallapp.data.model.auth.DoctorRegisterRequestDM
import com.example.carecallapp.data.model.auth.HospitalRegisterResponseDM
import com.example.carecallapp.data.model.auth.LoginRequestDM
import com.example.carecallapp.data.model.auth.TokenResponseDM
import com.example.carecallapp.data.model.hospital.hospital_accountsDM.PersonServiceResponseDM
import com.example.carecallapp.data.model.hospital.hospital_notification.HospitalNotificationResponseDM
import com.example.carecallapp.data.model.hospital.hospital_notification.ServiceDM
import com.example.carecallapp.data.model.hospital.hospital_profileDM.HospitalResponseDM
import com.example.carecallapp.data.model.hospital.hospital_services.BloodBagDM
import com.example.carecallapp.data.model.hospital.hospital_services.RoomAndNurseryResponseDM
import com.example.carecallapp.data.model.hospital.hospital_services.ServiceRequestDM
import com.example.carecallapp.data.model.hospital.hospital_services.ServiceResponseDM
import com.example.carecallapp.domain.model.person_service.LocationRequest
import com.example.carecallapp.domain.model.person_service.MapRouteDomain
import com.example.carecallapp.domain.model.person_service.PersonNotificationResponse
import com.example.carecallapp.domain.model.person_service.RequestStatus
import com.example.carecallapp.domain.model.person_service.ambulance.AmbulanceProfile
import com.example.carecallapp.domain.model.person_service.doctor.DoctorProfile
import com.example.carecallapp.domain.model.auth.AmbulanceRegisterRequest
import com.example.carecallapp.domain.model.auth.DoctorRegisterRequest
import com.example.carecallapp.domain.model.auth.HospitalRegisterRequest
import com.example.carecallapp.domain.model.auth.LoginRequest
import com.example.carecallapp.domain.model.auth.Role
import com.example.carecallapp.domain.model.auth.TokenResponse
import com.example.carecallapp.domain.model.hospital.hospital_accounts.PersonServiceResponse
import com.example.carecallapp.domain.model.hospital.hospital_content.BloodBag
import com.example.carecallapp.domain.model.hospital.hospital_content.RoomAndNursery
import com.example.carecallapp.domain.model.hospital.hospital_content.RoomStatus
import com.example.carecallapp.domain.model.hospital.hospital_content.RoomType
import com.example.carecallapp.domain.model.hospital.hospital_content.ServiceRequest
import com.example.carecallapp.domain.model.hospital.hospital_content.ServiceResponse
import com.example.carecallapp.domain.model.hospital.hospital_content.ServiceType
import com.example.carecallapp.domain.model.hospital.hospital_notification.HospitalNotificationResponse
import com.example.carecallapp.domain.model.hospital.hospital_notification.Service
import com.example.carecallapp.domain.model.hospital.hospital_profile.HospitalResponse
import com.google.android.gms.maps.model.LatLng

//----------------HOSPITAL---------------------
//Accounts
fun PersonServiceResponseDM.toPeopleService(): PersonServiceResponse {
    return PersonServiceResponse(
        userName = userName ?: "",
        phoneNumber = phoneNumber ?: "0",
        id = id
    )
}

//Profile
fun HospitalResponseDM.toHospitalResponse(): HospitalResponse {
    return HospitalResponse(
        profilePicture = profilePicture ?: "",
        firstName = this.firstName ?: "",
        lastName = this.lastName ?: "",
        website = this.website ?: "",
        nationalId = this.nationalId ?: "",
        gender = this.gender ?: "",
        phone = this.phone ?: 0,
        dateOfBirth = this.dateOfBirth ?: "",
        email = this.email ?: "",
        username = this.username ?: ""
    )
}

fun HospitalResponse.toHospitalResponseDM(): HospitalResponseDM {
    return HospitalResponseDM(
        profilePicture = profilePicture,
        firstName = this.firstName,
        lastName = this.lastName,
        website = this.website,
        nationalId = this.nationalId,
        gender = this.gender,
        phone = this.phone,
        dateOfBirth = this.dateOfBirth,
        email = this.email,
        username = this.username
    )
}


//SERVICES
fun ServiceRequest.toDataModel(): ServiceRequestDM {
    return ServiceRequestDM(
        price = this.price,
        name = this.name,
        description = this.description,
        serviceTypeId = this.serviceTypeId
    )
}

fun ServiceResponseDM.toDomainModel(): ServiceResponse {
    return ServiceResponse(
        serviceType = serviceType ?: "",
        price = this.price ?: 0,
        name = this.name.orEmpty(),
        description = this.description.orEmpty(),
        serviceTypeId = this.serviceTypeId ?: 0,
        id = this.id ?: 0
    )
}

//Blood bank
fun BloodBagDM.toBloodBag(): BloodBag {
    return BloodBag(
        id = id!!, bloodType = bloodType ?: "", bloodBagQuantity = quantity ?: 0
    )
}

fun BloodBag.toBloodDM(): BloodBagDM {
    return BloodBagDM(
        bloodType = bloodType, quantity = bloodBagQuantity
    )
}

//Room and Nursery
fun RoomAndNursery.toRoomAndNurseryDM(): RoomAndNurseryResponseDM {
    return RoomAndNurseryResponseDM(
        bedNumber = roomNumber,
        status = status.name,
        type = type.name
    )
}

fun RoomAndNurseryResponseDM.toRoomAndNursery(): RoomAndNursery {
    return RoomAndNursery(
        id = id!!,
        roomNumber = bedNumber ?: "0",
        status = when (status) {
            "Available" -> RoomStatus.Available
            "Occupied" -> RoomStatus.Occupied
            "Reserved" -> RoomStatus.Reserved
            "UnderMaintenance" -> RoomStatus.UnderMaintenance
            else -> RoomStatus.Available
        },
        type = when (type) {
            "ICU" -> RoomType.ICU
            "Nursery" -> RoomType.Nursery
            else -> RoomType.ICU
        }
    )
}


//AUTHENTICATION

fun AmbulanceRegisterRequest.toDataModel(): AmbulanceRegisterRequestDM {
    return AmbulanceRegisterRequestDM().apply {
        firstName = this@toDataModel.firstName
        lastName = this@toDataModel.lastName
        password = this@toDataModel.password
        nationalId = this@toDataModel.nationalId
        gender = this@toDataModel.gender
        phone = this@toDataModel.phone
        hospitalId = this@toDataModel.hospitalId
        confirmPassword = this@toDataModel.confirmPassword
        vehicleNumber = this@toDataModel.vehicleNumber
        dateOfBirth = this@toDataModel.dateOfBirth
        email = this@toDataModel.email
        username = this@toDataModel.username
    }
}

fun DoctorRegisterRequest.toDataModel(): DoctorRegisterRequestDM {
    return DoctorRegisterRequestDM().apply {
        lastName = this@toDataModel.lastName
        specialty = this@toDataModel.specialty
        gender = this@toDataModel.gender
        bio = this@toDataModel.bio
        dateOfBirth = this@toDataModel.dateOfBirth
        firstName = this@toDataModel.firstName
        password = this@toDataModel.password
        nationalId = this@toDataModel.nationalId
        phone = this@toDataModel.phone
        hospitalId = this@toDataModel.hospitalId
        confirmPassword = this@toDataModel.confirmPassword
        licenseNumber = this@toDataModel.licenseNumber
        email = this@toDataModel.email
        username = this@toDataModel.username
        status = this@toDataModel.status
    }
}

fun HospitalRegisterRequest.toDataModel(): HospitalRegisterResponseDM {
    return HospitalRegisterResponseDM().apply {
        firstName = this@toDataModel.firstName
        lastName = this@toDataModel.lastName
        password = this@toDataModel.password
        website = this@toDataModel.website
        nationalId = this@toDataModel.nationalId
        gender = this@toDataModel.gender
        phone = this@toDataModel.phone
        confirmPassword = this@toDataModel.confirmPassword
        dateOfBirth = this@toDataModel.dateOfBirth
        email = this@toDataModel.email
        username = this@toDataModel.username
    }
}

fun LoginRequest.toLoginDM(): LoginRequestDM {
    return LoginRequestDM(
        email = email, password = password
    )
}

fun TokenResponseDM.toLoginResponse(): TokenResponse {
    return TokenResponse(
        role = when (role) {
            Role.Patient.name -> Role.Patient
            Role.Admin.name -> Role.Admin
            Role.Doctor.name -> Role.Doctor
            Role.Hospital.name -> Role.Hospital
            Role.Ambulance.name -> Role.Ambulance
            else -> {
                Role.Hospital
            }

        }, expiration = expiration ?: "", userId = userId, token = token
    )
}


//NOTIFICATION REQUESTES

fun PersonNotificationResponseDM.toDomain(): PersonNotificationResponse {
    return PersonNotificationResponse(
        id = id,
        date = date,
        patientName = patientName?.toString(),
        quantity = quantity?.toString(),
        patientId = patientId,
        latitude = latitude,
        longitude = longitude,
        targetLongitude = targetlongitude?.toString()?.toDoubleOrNull(),
        targetLatitude = targetlatitude?.toString()?.toDoubleOrNull(),
        phoneNumber = phoneNumber?.toString(),
        price = price,
        patient = patient?.toString(),
        patientNationalId = patientNationalId,
        caseDescription = jsonMemberCase?.toString(),
        status = status?.let {
            when (status) {
                "Pending" -> RequestStatus.Pending
                "Accepted" -> RequestStatus.Accepted
                "Rejected" -> RequestStatus.Rejected
                "Confirmed" -> RequestStatus.Confirmed
                "Completed" -> RequestStatus.Completed
                "Canceled" -> RequestStatus.Canceled
                else -> null
            }
        }
    )
}

fun LocationRequest.toDataModel(): LocationRequestDM {
    return LocationRequestDM(
        latitude = latitude,
        longitude = longitude,

        )
}

// ServiceDM.kt
fun ServiceDM.toDomain(): Service {
    return Service(
        name = name?:"",
        serviceType = when (this.serviceType) {
            "Ambulance" -> ServiceType.Ambulance
            "Doctor" -> ServiceType.Doctor
            "Nursery" -> ServiceType.Nursery
            "BloodBank" -> ServiceType.BloodBank
            "ICU" -> ServiceType.ICU
            else -> ServiceType.Ambulance // عشان هو Any؟
        },
        id = this.id
    )
}

fun HospitalNotificationResponseDM.toDomain(): HospitalNotificationResponse {
    return HospitalNotificationResponse(
        id = this.id ?: 0,
        date = this.date,
        patientName = this.patientName?.toString(),
        quantity = this.quantity?.toString(),
        bloodType = this.bloodType?.toString(),
        phoneNumber = this.phoneNumber?.toString(),
        price = this.price,
        service = service?.toDomain(),
        caseDescription = this.jsonMemberCase?.toString(),
        status = when (this.status) {
            "Pending" -> RequestStatus.Pending
            "Accepted" -> RequestStatus.Accepted
            "Rejected" -> RequestStatus.Rejected
            "Confirmed" -> RequestStatus.Confirmed
            "Completed" -> RequestStatus.Completed
            "Canceled" -> RequestStatus.Canceled
            else -> null
        }
    )
}


//--------------------------PERSON DOCTOR AND AMBULANCE---------------------

//DOCTOR
fun DoctorProfile.toDataModel(): DoctorProfileDM {
    return DoctorProfileDM(
        lastName = this.lastName,
        specialty = this.specialty,
        gender = this.gender,
        bio = this.bio,
        dateOfBirth = this.dateOfBirth,
        profilePicture = this.profilePicture,
        firstName = this.firstName,
        nationalId = this.nationalId,
        phone = this.phone,
        hospitalId = this.hospitalId,
        licenseNumber = this.licenseNumber,
        email = this.email,
        username = this.username,
        status = this.status
    )
}

fun DoctorProfileDM.toDomainModel(): DoctorProfile {
    return DoctorProfile(
        lastName = this.lastName,
        specialty = this.specialty,
        gender = this.gender,
        bio = this.bio,
        dateOfBirth = this.dateOfBirth,
        profilePicture = this.profilePicture,
        firstName = this.firstName,
        nationalId = this.nationalId,
        phone = this.phone,
        hospitalId = this.hospitalId,
        licenseNumber = this.licenseNumber,
        email = this.email,
        username = this.username,
        status = this.status
    )
}

//AMBULANCE
fun AmbulanceProfile.toDataModel(): AmbulanceProfileDM {
    return AmbulanceProfileDM(
        profilePicture = this.profilePicture,
        firstName = this.firstName,
        lastName = this.lastName,
        nationalId = this.nationalId,
        gender = this.gender,
        phone = this.phone,
        vehicleNumber = this.vehicleNumber,
        dateOfBirth = this.dateOfBirth,
        email = this.email,
        username = this.username,
        status = this.status
    )
}

fun AmbulanceProfileDM.toDomainModel(): AmbulanceProfile {
    return AmbulanceProfile(
        profilePicture = this.profilePicture,
        firstName = this.firstName,
        lastName = this.lastName,
        nationalId = this.nationalId,
        gender = this.gender,
        phone = this.phone,
        vehicleNumber = this.vehicleNumber,
        dateOfBirth = this.dateOfBirth,
        email = this.email,
        username = this.username,
        status = this.status
    )
}


// MAP ROUTE
fun MapRouteResponseDM.toDomain(): MapRouteDomain {
    val path = routes?.firstOrNull()?.geometry?.coordinates?.map {
        LatLng(it[1], it[0]) // [longitude, latitude] → [latitude, longitude]
    } ?: emptyList()

    return MapRouteDomain(path = path)
}
