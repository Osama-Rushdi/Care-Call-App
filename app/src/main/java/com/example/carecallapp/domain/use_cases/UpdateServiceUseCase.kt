package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital_content.ServiceRequest
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class UpdateServiceUseCase @Inject constructor(val repository: HospitalRepository) {
    suspend fun execute(id: Int, service: ServiceRequest): Boolean {
        return repository.updateService(id, service)
    }
}