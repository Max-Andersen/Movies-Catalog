package com.example.moviecatalog.network.Auth

@kotlinx.serialization.Serializable
data class TokenResponse(
    val token: String
)