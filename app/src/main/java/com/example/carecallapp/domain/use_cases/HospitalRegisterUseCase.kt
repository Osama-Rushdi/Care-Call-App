package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.auth.HospitalRegisterResponse
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class HospitalRegisterUseCase @Inject constructor(private val repository: HospitalRepository) {
    suspend fun execute(hospitalRegisterResponse: HospitalRegisterResponse): Boolean {
        return repository.hospitalRegister(hospitalRegisterResponse)
    }
}