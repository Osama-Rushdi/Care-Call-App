package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.auth.DoctorRegisterResponse
import com.example.carecallapp.domain.model.auth.LoginRequest
import com.example.carecallapp.domain.model.auth.LoginResponse
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: HospitalRepository) {
    suspend fun execute(loginRequest: LoginRequest): LoginResponse {
        return repository.userLogin(loginRequest)
    }
}