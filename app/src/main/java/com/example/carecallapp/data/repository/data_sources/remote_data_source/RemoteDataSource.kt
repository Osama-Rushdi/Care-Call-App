package com.example.carecallapp.data.repository.data_sources.remote_data_source
import com.example.carecallapp.domain.model.hospital_accounts.PersonServiceResponse
import com.example.carecallapp.domain.model.hospital_profile.HospitalResponse


interface RemoteDataSource {
    suspend fun getPeopleAccounts(
        accountType: String, hospitalId: String
    ): List<PersonServiceResponse>

    suspend fun getHospitalDetails( hospitalId: String): HospitalResponse

    suspend fun updateHospitalDetails( hospitalId: String,hospitalResponse: HospitalResponse): HospitalResponse

}
