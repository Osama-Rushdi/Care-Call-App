package com.example.carecallapp.data.repository

import com.example.carecallapp.data.repository.data_sources.remote_data_source.RemoteDataSource
import com.example.carecallapp.domain.model.hospital_accounts.PersonServiceResponse
import com.example.carecallapp.domain.model.hospital_profile.HospitalResponse
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject


class HospitalRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val connectivity: Connectivity,
) : HospitalRepository {

    override suspend fun getPeopleAccounts(
        accountType: String, hospitalId: String
    ): List<PersonServiceResponse> {
        return if (connectivity.isOnline()) {
            remoteDataSource.getPeopleAccounts(accountType, hospitalId)
        } else
            throw Exception("no Internet")
    }

    override suspend fun getHospitalDetails(hospitalId: String): HospitalResponse {
        return if (connectivity.isOnline()) {
            remoteDataSource.getHospitalDetails(hospitalId)
        } else
            throw Exception("no Internet")
    }

    override suspend fun updateHospitalDetails(hospitalId: String,hospitalResponse: HospitalResponse): HospitalResponse {
        return if (connectivity.isOnline()) {
            remoteDataSource.updateHospitalDetails(hospitalId,hospitalResponse)
        } else
            throw Exception("no Internet")
    }
}
