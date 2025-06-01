package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital.hospital_content.ServiceRequest
import com.example.carecallapp.domain.repository.MyRepository
import javax.inject.Inject

class UpdateServiceUseCase @Inject constructor(val repository: MyRepository) {
    suspend fun execute(id: Int, service: ServiceRequest): Boolean {
        return repository.updateService(id, service)
    }
}