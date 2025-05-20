package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.auth.LoginRequest
import com.example.carecallapp.domain.model.auth.TokenResponse
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class UserLoginUseCase @Inject constructor(private val repository: HospitalRepository) {
    suspend fun execute(loginRequest: LoginRequest): TokenResponse {
        return repository.userLogin(loginRequest)
    }
}