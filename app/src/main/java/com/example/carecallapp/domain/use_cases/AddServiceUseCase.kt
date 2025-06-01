package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital.hospital_content.ServiceRequest
import com.example.carecallapp.domain.model.hospital.hospital_content.ServiceResponse
import com.example.carecallapp.domain.repository.MyRepository
import javax.inject.Inject

class AddServiceUseCase @Inject constructor(val repository: MyRepository){
     suspend fun execute(service: ServiceRequest): ServiceResponse {
         return repository.addService(service)
         }
     }