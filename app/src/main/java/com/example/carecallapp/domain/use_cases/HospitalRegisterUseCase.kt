package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.auth.HospitalRegisterRequest
import com.example.carecallapp.domain.repository.MyRepository
import javax.inject.Inject

class HospitalRegisterUseCase @Inject constructor(private val repository: MyRepository) {
    suspend fun execute(hospitalRegisterResponse: HospitalRegisterRequest): Boolean {
        return repository.hospitalRegister(hospitalRegisterResponse)
    }
}