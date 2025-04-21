package com.example.carecallapp.data.repository.data_sources.remote_data_source

import com.example.carecallapp.data.api.WebServices
import com.example.carecallapp.data.mappers.toPeopleService
import com.example.carecallapp.domain.model.hospital_accounts.PersonService

import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val webServices: WebServices) :
    RemoteDataSource {
    override suspend fun getPeopleAccounts(
        accountType: String,
        hospitalId: String
    ): List<PersonService> {
        return webServices.getPeopleAccounts(accountType, hospitalId=hospitalId).map { it.toPeopleService() }
    }


}
