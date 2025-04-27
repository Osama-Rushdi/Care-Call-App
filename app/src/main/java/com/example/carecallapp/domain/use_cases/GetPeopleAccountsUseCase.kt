package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital_accounts.PersonServiceResponse
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class GetPeopleAccountsUseCase @Inject constructor(private val repository: HospitalRepository) {
    suspend fun execute(
        accountType: String, hospitalId: String
    ): List<PersonServiceResponse> {
        return repository.getPeopleAccounts(accountType, hospitalId)
    }
}