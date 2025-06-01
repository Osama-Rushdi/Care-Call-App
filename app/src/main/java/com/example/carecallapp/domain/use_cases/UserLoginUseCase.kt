package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.auth.LoginRequest
import com.example.carecallapp.domain.model.auth.TokenResponse
import com.example.carecallapp.domain.repository.MyRepository
import javax.inject.Inject

class UserLoginUseCase @Inject constructor(private val repository: MyRepository) {
    suspend fun execute(loginRequest: LoginRequest): TokenResponse {
        return repository.userLogin(loginRequest)
    }
}