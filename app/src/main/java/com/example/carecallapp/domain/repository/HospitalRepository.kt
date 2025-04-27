package com.example.carecallapp.domain.repository

import com.example.carecallapp.domain.model.hospital_accounts.PersonServiceResponse
import com.example.carecallapp.domain.model.hospital_content.BloodBag
import com.example.carecallapp.domain.model.hospital_profile.HospitalResponse

interface HospitalRepository {
    suspend fun getPeopleAccounts(
        accountType: String, hospitalId: String
    ): List<PersonServiceResponse>

    suspend fun getHospitalDetails( hospitalId: String): HospitalResponse

    suspend fun updateHospitalDetails( hospitalId: String,hospitalResponse: HospitalResponse): HospitalResponse

    suspend fun getAll(): List<BloodBag>
    suspend fun getBloodById(id: Int): BloodBag
    suspend fun addBlood(blood: BloodBag): BloodBag
    suspend fun updateBlood(id: Int, blood: BloodBag): BloodBag
    suspend fun deleteBlood(id: Int): Boolean

}