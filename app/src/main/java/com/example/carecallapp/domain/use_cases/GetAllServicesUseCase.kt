package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital_content.ServiceResponse
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class GetAllServicesUseCase @Inject constructor(private val repository: HospitalRepository) {
    suspend fun execute(): List<ServiceResponse> {
        return repository.getAllServices()
    }
}