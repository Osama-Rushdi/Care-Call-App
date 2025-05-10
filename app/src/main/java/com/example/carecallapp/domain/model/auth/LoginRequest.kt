package com.example.carecallapp.domain.model.auth


data class LoginRequest(
    val password: String = "",
    val email: String = ""
)