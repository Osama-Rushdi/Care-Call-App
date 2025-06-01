package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital.hospital_content.ServiceResponse
import com.example.carecallapp.domain.repository.MyRepository
import javax.inject.Inject

class GetAllServicesUseCase @Inject constructor(private val repository: MyRepository) {
    suspend fun execute(): List<ServiceResponse> {
        return repository.getAllServices()
    }
}