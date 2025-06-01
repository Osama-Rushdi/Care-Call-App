package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital.hospital_profile.HospitalResponse
import com.example.carecallapp.domain.repository.MyRepository
import javax.inject.Inject

class UpdateHospitalDetailsUseCase @Inject constructor(private val repository: MyRepository) {
    suspend fun execute(hospitalId: String,hospitalResponse: HospitalResponse): HospitalResponse {
        return repository.updateHospitalDetails(hospitalId,hospitalResponse)
    }
}