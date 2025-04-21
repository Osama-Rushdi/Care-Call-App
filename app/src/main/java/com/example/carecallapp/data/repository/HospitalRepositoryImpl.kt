package com.example.carecallapp.data.repository

import com.example.carecallapp.data.repository.data_sources.remote_data_source.RemoteDataSource
import com.example.carecallapp.domain.model.hospital_accounts.PersonService
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject


class HospitalRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val connectivity: Connectivity,
) : HospitalRepository {

   override suspend fun getPeopleAccounts(accountType: String, hospitalId: String
    ): List<PersonService> {
        return if (connectivity.isOnline()) {
            remoteDataSource.getPeopleAccounts(accountType,hospitalId)
        } else
            throw Exception("no Internet")
    }


}