package com.example.carecallapp.data.repository

import com.example.carecallapp.data.repository.data_sources.remote_data_source.RemoteDataSource
import com.example.carecallapp.domain.model.hospital_accounts.Accounts
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject


class HospitalRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val connectivity: Connectivity,
) : HospitalRepository {


    override fun getAccounts(sourceId: String?, token: String?): List<Accounts> {
        return if (connectivity.isOnline()) {
            remoteDataSource.getAccounts(sourceId, token)
        } else
            emptyList()
        //todo it will be init after i get accounts from backend
    }

}