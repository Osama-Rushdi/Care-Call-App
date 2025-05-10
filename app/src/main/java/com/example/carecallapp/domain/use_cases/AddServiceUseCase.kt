package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital_content.ServiceRequest
import com.example.carecallapp.domain.model.hospital_content.ServiceResponse
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class AddServiceUseCase @Inject constructor(val repository: HospitalRepository){
     suspend fun execute(service: ServiceRequest): ServiceResponse {
         return repository.addService(service)
         }
     }