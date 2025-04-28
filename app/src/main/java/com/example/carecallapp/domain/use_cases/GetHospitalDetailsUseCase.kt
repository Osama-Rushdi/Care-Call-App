package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital_profile.HospitalResponse
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class GetHospitalDetailsUseCase  @Inject constructor(private val repository: HospitalRepository)  {

    suspend fun execute(hospitalId: String): HospitalResponse {
        return repository.getHospitalDetails(hospitalId)
    }


}