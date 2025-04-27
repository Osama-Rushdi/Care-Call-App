package com.example.carecallapp.data.mappers

import com.example.carecallapp.data.model.hospital_accountsDM.PersonServiceResponseDM
import com.example.carecallapp.data.model.hospital_profileDM.HospitalResponseDM
import com.example.carecallapp.data.model.hospital_services.BloodBagDM
import com.example.carecallapp.domain.model.hospital_accounts.PersonServiceResponse
import com.example.carecallapp.domain.model.hospital_content.BloodBag
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

fun BloodBagDM.toBloodBag(): BloodBag {
    return BloodBag(
        id = id!! , bloodType = bloodType ?: "", bloodBagQuantity = quantity ?: 0
    )
}

fun BloodBag.toBloodDM(): BloodBagDM {
    return BloodBagDM(
        bloodType = bloodType, quantity = bloodBagQuantity
    )
}
