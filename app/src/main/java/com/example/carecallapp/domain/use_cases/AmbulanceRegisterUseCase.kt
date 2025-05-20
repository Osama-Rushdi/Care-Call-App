package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.auth.AmbulanceRegisterRequest
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class AmbulanceRegisterUseCase @Inject constructor(private val repository: HospitalRepository) {
    suspend fun execute(ambulanceRegisterResponse: AmbulanceRegisterRequest): Boolean {
        return repository.ambulanceRegister(ambulanceRegisterResponse)
    }
}