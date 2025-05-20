package com.example.carecallapp.domain.model.auth


data class TokenResponse(
    val role: String = "",
    val expiration: String = "",
    val userId: String?,
    val token: String?
)