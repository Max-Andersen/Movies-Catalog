package com.example.moviecatalog.network.Auth


@kotlinx.serialization.Serializable
data class RegisterRequestBody(
    val userName: String,
    val name: String,
    val password: String,
    val email: String,
    val birthDate: String,
    val gender: Int
)
