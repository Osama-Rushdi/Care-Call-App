package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital_profile.HospitalResponse
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class UpdateHospitalDetailsUseCase @Inject constructor(private val repository: HospitalRepository) {
    suspend fun execute(hospitalId: String,hospitalResponse: HospitalResponse): HospitalResponse {
        return repository.updateHospitalDetails(hospitalId,hospitalResponse)
    }
}