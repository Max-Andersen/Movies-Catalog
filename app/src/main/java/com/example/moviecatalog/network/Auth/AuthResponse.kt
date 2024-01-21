package com.example.moviecatalog.network.Auth

sealed class AuthResponse {
    data class Success(val token: String): AuthResponse()

    data class Fail(val cause: String): AuthResponse()
}