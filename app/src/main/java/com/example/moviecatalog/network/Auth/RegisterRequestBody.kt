package com.example.moviecatalog.network.Auth

import java.time.ZonedDateTime

@kotlinx.serialization.Serializable
data class RegisterRequestBody(
    val userName: String,
    val name: String,
    val password: String,
    val email: String,
    val birthDate: ZonedDateTime,
    val gender: Int
)
