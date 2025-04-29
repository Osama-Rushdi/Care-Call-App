package com.example.carecallapp.data.mappers

import com.example.carecallapp.data.model.hospital_accountsDM.PersonServiceResponseDM
import com.example.carecallapp.data.model.hospital_profileDM.HospitalResponseDM
import com.example.carecallapp.data.model.hospital_services.BloodBagDM
import com.example.carecallapp.data.model.hospital_services.RoomAndNurseryDM
import com.example.carecallapp.domain.model.hospital_accounts.PersonServiceResponse
import com.example.carecallapp.domain.model.hospital_content.BloodBag
import com.example.carecallapp.domain.model.hospital_content.RoomAndNursery
import com.example.carecallapp.domain.model.hospital_content.RoomType
import com.example.carecallapp.domain.model.hospital_content.Status
import com.example.carecallapp.domain.model.hospital_profile.HospitalResponse

fun PersonServiceResponseDM.toPeopleService(): PersonServiceResponse {
    return PersonServiceResponse(
        userName = userName ?: "",
        phoneNumber = phoneNumber ?: "0",
        id = id
    )
}

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

