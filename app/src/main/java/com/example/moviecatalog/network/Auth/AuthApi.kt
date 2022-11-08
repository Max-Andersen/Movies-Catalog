package com.example.moviecatalog.network.Auth

import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApi {

    @POST("account/register")
    suspend fun register(@Body body: RegisterRequestBody): TokenResponse

    @POST("account/login")
    suspend fun login(@Body body: LoginRequestBody): TokenResponse
}