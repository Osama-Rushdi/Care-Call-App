package com.example.carecallapp.data.mappers

import com.example.carecallapp.data.model.hospital_accountsDM.PersonServiceDM
import com.example.carecallapp.domain.model.hospital_accounts.PersonService


fun PersonServiceDM.toPeopleService(): PersonService {
    return PersonService(
        userName = userName ?: "",
        phoneNumber = phoneNumber?:"0",
        id = id
    )
}