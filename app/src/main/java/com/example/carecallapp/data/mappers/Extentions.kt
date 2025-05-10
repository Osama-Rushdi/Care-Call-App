package com.example.carecallapp.data.mappers

import com.example.carecallapp.data.model.auth.AmbulanceRegisterResponseDM
import com.example.carecallapp.data.model.auth.DoctorRegisterResponseDM
import com.example.carecallapp.data.model.auth.HospitalRegisterResponseDM
import com.example.carecallapp.data.model.auth.LoginRequestDM
import com.example.carecallapp.data.model.auth.LoginResponseDM
import com.example.carecallapp.data.model.hospital_accountsDM.PersonServiceResponseDM
import com.example.carecallapp.data.model.hospital_profileDM.HospitalResponseDM
import com.example.carecallapp.data.model.hospital_services.BloodBagDM
import com.example.carecallapp.data.model.hospital_services.RoomAndNurseryDM
import com.example.carecallapp.data.model.hospital_services.ServiceRequestDM
import com.example.carecallapp.data.model.hospital_services.ServiceResponseDM
import com.example.carecallapp.domain.ServiceTypeUtil
import com.example.carecallapp.domain.model.hospital_accounts.PersonServiceResponse
import com.example.carecallapp.domain.model.hospital_content.BloodBag
import com.example.carecallapp.domain.model.hospital_content.RoomAndNursery
import com.example.carecallapp.domain.model.hospital_content.RoomType
import com.example.carecallapp.domain.model.hospital_content.Status
import com.example.carecallapp.domain.model.hospital_profile.HospitalResponse
import com.example.carecallapp.domain.model.auth.AmbulanceRegisterResponse
import com.example.carecallapp.domain.model.auth.DoctorRegisterResponse
import com.example.carecallapp.domain.model.auth.HospitalRegisterResponse
import com.example.carecallapp.domain.model.auth.LoginRequest
import com.example.carecallapp.domain.model.auth.LoginResponse
import com.example.carecallapp.domain.model.hospital_content.ServiceRequest
import com.example.carecallapp.domain.model.hospital_content.ServiceResponse
import com.example.carecallapp.domain.model.hospital_content.ServiceType


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

fun ServiceRequestDM.toDomainModel(): ServiceRequest {
    return ServiceRequest(
        price = this.price ?: 0,
        name = this.name.orEmpty(),
        description = this.description.orEmpty(),
        serviceTypeId = this.serviceTypeId ?: 0
    )
}

fun ServiceResponse.toDataModel(hospitalId: String? = null): ServiceResponseDM {
    return ServiceResponseDM(
        serviceType = this.serviceType,
        hospitalId = hospitalId,
        price = this.price,
        name = this.name,
        description = this.description,
        serviceTypeId = this.serviceTypeId,
        id = this.id
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
fun RoomAndNursery.toRoomAndNurseryDM(): RoomAndNurseryDM {
    return RoomAndNurseryDM(
        bedNumber = roomNumber,
        status = status.name,
        type = type.name
    )
}

fun RoomAndNurseryDM.toRoomAndNursery(): RoomAndNursery {
    return RoomAndNursery(
        id = id!!,
        roomNumber = bedNumber ?: "0",
        status = when (status) {
            "Available" -> Status.Available
            "NotAvailable" -> Status.NotAvailable
            else -> Status.Available
        },
        type = when (type) {
            "ICU" -> RoomType.ICU
            "Nursery" -> RoomType.Nursery
            else -> RoomType.ICU
        }
    )
}


//AUTHENTICATION
fun AmbulanceRegisterResponseDM.toDomainModel(): AmbulanceRegisterResponse {
    return AmbulanceRegisterResponse(
        firstName = firstName ?: "",
        lastName = lastName ?: "",
        password = password ?: "",
        nationalId = nationalId ?: "",
        gender = gender ?: "",
        phone = phone,
        hospitalId = hospitalId ?: "",
        confirmPassword = confirmPassword ?: "",
        vehicleNumber = vehicleNumber ?: "",
        dateOfBirth = dateOfBirth ?: "",
        email = email ?: "",
        username = username ?: ""
    )
}

fun DoctorRegisterResponseDM.toDomainModel(): DoctorRegisterResponse {
    return DoctorRegisterResponse(
        lastName = lastName ?: "",
        specialty = specialty ?: "",
        gender = gender ?: "",
        bio = bio ?: "",
        dateOfBirth = dateOfBirth ?: "",
        firstName = firstName ?: "",
        password = password ?: "",
        nationalId = nationalId ?: "",
        phone = phone,
        hospitalId = hospitalId ?: "",
        confirmPassword = confirmPassword ?: "",
        licenseNumber = licenseNumber ?: "",
        email = email ?: "",
        username = username ?: "",
        status = status ?: ""
    )
}

fun HospitalRegisterResponseDM.toDomainModel(): HospitalRegisterResponse {
    return HospitalRegisterResponse(
        firstName = firstName ?: "",
        lastName = lastName ?: "",
        password = password ?: "",
        website = website ?: "",
        nationalId = nationalId ?: "",
        gender = gender ?: "",
        phone = phone ?: 0,
        confirmPassword = confirmPassword ?: "",
        dateOfBirth = dateOfBirth ?: "",
        email = email ?: "",
        username = username ?: ""
    )
}

fun AmbulanceRegisterResponse.toDataModel(): AmbulanceRegisterResponseDM {
    return AmbulanceRegisterResponseDM().apply {
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

fun DoctorRegisterResponse.toDataModel(): DoctorRegisterResponseDM {
    return DoctorRegisterResponseDM().apply {
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

fun HospitalRegisterResponse.toDataModel(): HospitalRegisterResponseDM {
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

fun LoginRequestDM.toLogin(): LoginRequest {
    return LoginRequest(
        email = email ?: "", password = password ?: ""
    )
}

fun LoginResponseDM.toLoginResponse(): LoginResponse {
    return LoginResponse(
        role = role ?: "", expiration = expiration ?: "", userId = userId, token = token
    )
}


