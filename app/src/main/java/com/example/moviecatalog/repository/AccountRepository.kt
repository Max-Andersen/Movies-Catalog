package com.example.moviecatalog.repository

import com.example.moviecatalog.network.Auth.AuthApi
import com.example.moviecatalog.network.Auth.LoginRequestBody
import com.example.moviecatalog.network.Network
import com.example.moviecatalog.network.Auth.TokenResponse

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AccountRepository {

    private val api: AuthApi = Network.getAuthApi()

    fun register(
        login: String,
        name: String,
        password: String,
        email: String,
        birthdate: String,
        gender: String
    ): String {
        //make request on url with login, name, etc.
        return "Success"
    }


//    fun register(body: RegisterRequestBody): Flow<TokenResponse> = flow{
//        val tokenData = api.register(body)
//        emit(tokenData)
//    }.flowOn(Dispatchers.IO)


    fun login(username: String, password: String): String {
        //make request on url with login and password
        return "Success"
    }

    fun loggin(body: LoginRequestBody): Flow<Result<TokenResponse>> = flow {
        try {
            val tokenData = api.login(body)
            Network.token = tokenData.toString()
            emit(Result.success(tokenData) )
        }
        catch (e: java.lang.Exception){
            emit(Result.failure(e))
        }

    }.flowOn(Dispatchers.IO)


    fun Logout() {

    }
}