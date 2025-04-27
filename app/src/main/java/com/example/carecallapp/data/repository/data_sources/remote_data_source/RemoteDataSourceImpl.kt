package com.example.carecallapp.data.repository.data_sources.remote_data_source

import com.example.carecallapp.data.api.WebServices
import com.example.carecallapp.data.mappers.toBloodBag
import com.example.carecallapp.data.mappers.toBloodDM
import com.example.carecallapp.data.mappers.toHospitalResponse
import com.example.carecallapp.data.mappers.toHospitalResponseDM
import com.example.carecallapp.data.mappers.toPeopleService
import com.example.carecallapp.domain.model.hospital_accounts.PersonServiceResponse
import com.example.carecallapp.domain.model.hospital_content.BloodBag
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

    override suspend fun getHospitalDetails(hospitalId: String): HospitalResponse =
        webServices.getHospitalDetails(hospitalId)!!.toHospitalResponse()

    override suspend fun updateHospitalDetails(
        hospitalId: String,
        hospitalResponse: HospitalResponse
    ): HospitalResponse {
        val response =
            webServices.updateHospitalDetails(hospitalId, hospitalResponse.toHospitalResponseDM())
        if (response.isSuccessful) {
            return hospitalResponse // لأنه فعليًا اتحدث بنجاح ومفيش داتا جديدة هترجع
        } else {
            throw Exception("Update failed with code: ${response.code()}")
        }
    }

    override suspend fun getBloodAll(): List<BloodBag> = webServices.getAll().map { it.toBloodBag() }

    override suspend fun addBlood(blood: BloodBag): BloodBag {
        val response = webServices.addBlood(blood.toBloodDM())
        return if (response.isSuccessful) blood
        else
            throw Exception("add blood failed with code: ${response.code()}")
    }

    override suspend fun updateBlood(id: Int, blood: BloodBag): BloodBag {
        val response = webServices.updateBlood(id, blood.toBloodDM())
        return if (response.isSuccessful) blood
        else {
            throw Exception("update blood failed with code: ${response.code()}")
        }
    }

    override suspend fun deleteBlood(id: Int): Boolean {
        val response = webServices.deleteBlood(id)
        return if (response.isSuccessful) true
        else
            throw Exception("delete blood failed with code: ${response.code()}")
    }

    override suspend fun getBloodById(id: Int): BloodBag = webServices.getById(id).toBloodBag()
}
