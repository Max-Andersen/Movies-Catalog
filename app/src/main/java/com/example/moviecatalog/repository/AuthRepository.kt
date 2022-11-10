package com.example.moviecatalog.repository

import com.example.moviecatalog.network.Auth.AuthApi
import com.example.moviecatalog.network.Auth.LoginRequestBody
import com.example.moviecatalog.network.Auth.RegisterRequestBody
import com.example.moviecatalog.network.Network
import com.example.moviecatalog.network.Auth.TokenResponse
import kotlinx.coroutines.CoroutineScope

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class AuthRepository {

    private val api: AuthApi = Network.getAuthApi()

    fun register(body: RegisterRequestBody): Flow<Result<TokenResponse>> = flow{
        try {
            val tokenData = api.register(body)
            Network.token = tokenData.token
            emit(Result.success(tokenData) )
        }
        catch (e: java.lang.Exception){
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)


    fun login(body: LoginRequestBody): Flow<Result<TokenResponse>> = flow {
        try {
            val tokenData = api.login(body)
            Network.token = tokenData.token
            emit(Result.success(tokenData) )
        }
        catch (e: java.lang.Exception){
            emit(Result.failure(e))
        }

    }.flowOn(Dispatchers.IO)


    fun logout() {
        CoroutineScope(Dispatchers.IO).launch{
            api.logout()
            Network.token = ""
            Network.userId = ""
        }
    }
}