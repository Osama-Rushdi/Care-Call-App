package com.example.carecallapp.data.repository.data_sources.remote_data_source
import com.example.carecallapp.domain.model.hospital_accounts.PersonService


interface RemoteDataSource {
    suspend fun getPeopleAccounts(
        accountType: String, hospitalId: String
    ): List<PersonService>
  }
