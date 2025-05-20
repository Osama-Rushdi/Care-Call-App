package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.auth.HospitalRegisterRequest
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class HospitalRegisterUseCase @Inject constructor(private val repository: HospitalRepository) {
    suspend fun execute(hospitalRegisterResponse: HospitalRegisterRequest): Boolean {
        return repository.hospitalRegister(hospitalRegisterResponse)
    }
}