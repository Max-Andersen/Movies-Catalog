package com.example.moviecatalog.repository

import com.example.moviecatalog.clearUserData
import com.example.moviecatalog.network.Auth.AuthApi
import com.example.moviecatalog.network.Auth.AuthResponse
import com.example.moviecatalog.network.Auth.LoginRequestBody
import com.example.moviecatalog.network.Auth.RegisterRequestBody
import com.example.moviecatalog.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthRepository {

    private val api: AuthApi = Network.getAuthApi()

    suspend fun register(body: RegisterRequestBody): AuthResponse {
        return try {
            val token = api.register(body).token
            Network.updateToken(token)
            AuthResponse.Success(token)
        } catch (e: java.lang.Exception) {
            AuthResponse.Fail(e.message ?: "Unknown failure")
        }
    }


    suspend fun login(body: LoginRequestBody): AuthResponse {
        return try {
            val token = api.login(body).token
            Network.updateToken(token)
            AuthResponse.Success(token)
        } catch (e: java.lang.Exception) {
            AuthResponse.Fail(e.message ?: "Unknown failure")
        }
    }


    fun logout() {
        CoroutineScope(Dispatchers.IO).launch {
            api.logout()
            clearUserData()
        }
    }
}