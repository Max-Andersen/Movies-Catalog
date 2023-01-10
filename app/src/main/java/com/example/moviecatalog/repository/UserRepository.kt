package com.example.moviecatalog.repository

import UserDataResponse
import com.example.moviecatalog.network.Auth.TokenResponse
import com.example.moviecatalog.network.Network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository {

    private val userApi = Network.getUserApi()

    suspend fun getData(): Flow<Result<UserDataResponse>> = flow {
        try {
            val data = userApi.getProfileData()
            Network.userId = data.id
            emit(Result.success(data))
        } catch (e: java.lang.Exception) {
            emit(Result.failure(e))
        }

    }.flowOn(Dispatchers.IO)

    suspend fun putData(data: UserDataResponse) {
        userApi.putProfileData(data)
    }
}