package com.example.carecallapp.domain.model.auth


data class LoginResponse(

    val role: String = "",
    val expiration: String = "",
    val userId: String?,
    val token: String?
)