package com.example.carecallapp.domain.repository

import com.example.carecallapp.domain.model.hospital_accounts.PersonService

interface HospitalRepository {
    suspend fun getPeopleAccounts(
        accountType: String, hospitalId: String
    ): List<PersonService>

}