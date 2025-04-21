package com.example.carecallapp.data.repository.data_sources.remote_data_source

import com.example.carecallapp.data.api.WebServices
import com.example.carecallapp.data.mappers.toHospitalResponse
import com.example.carecallapp.data.mappers.toHospitalResponseDM
import com.example.carecallapp.data.mappers.toPeopleService
import com.example.carecallapp.domain.model.hospital_accounts.PersonServiceResponse
import com.example.carecallapp.domain.model.hospital_profile.HospitalResponse

import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val webServices: WebServices) :
    RemoteDataSource {
    override suspend fun getPeopleAccounts(
        accountType: String,
        hospitalId: String
    ): List<PersonServiceResponse> {
        return webServices.getPeopleAccounts(accountType, hospitalId = hospitalId)
            .map { it.toPeopleService() }
    }

    override suspend fun getHospitalDetails(hospitalId: String): HospitalResponse {
        return webServices.getHospitalDetails(hospitalId)!!.toHospitalResponse()
    }

    override suspend fun updateHospitalDetails(hospitalId: String,hospitalResponse: HospitalResponse): HospitalResponse {
        return webServices.updateHospitalDetails(hospitalId,hospitalResponse.toHospitalResponseDM())!!.toHospitalResponse()
    }


}
