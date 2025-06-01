package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital.hospital_accounts.PersonServiceResponse
import com.example.carecallapp.domain.repository.MyRepository
import javax.inject.Inject

class GetPeopleAccountsUseCase @Inject constructor(private val repository: MyRepository) {
    suspend fun execute(
        accountType: String, hospitalId: String
    ): List<PersonServiceResponse> {
        return repository.getPeopleAccounts(accountType, hospitalId)
    }
}