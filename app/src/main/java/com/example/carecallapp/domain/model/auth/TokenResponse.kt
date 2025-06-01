package com.example.carecallapp.domain.model.auth


data class TokenResponse(
    val role: Role,
    val expiration: String = "",
    val userId: String?,
    val token: String?
)

enum class Role {
    Hospital,
    Doctor,
    Ambulance,
    Patient,
    Admin
}