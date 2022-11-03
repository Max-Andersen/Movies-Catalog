package com.example.moviecatalog.network.Auth

import com.example.moviecatalog.network.TokenResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApi {

    @POST("api/account/register")
    suspend fun register(@Body body: RegisterRequestBody): TokenResponse

    @POST("api/account/login")
    suspend fun login(@Body body: LoginRequestBody): TokenResponse
}