package com.example.moviecatalog.network.Auth

@kotlinx.serialization.Serializable
data class LoginRequestBody (
    val username: String,
    val password: String
)