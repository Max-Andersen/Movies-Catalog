package com.example.moviecatalog.network.User

import UserDataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT


interface UserApi {

    @GET("account/profile")
    suspend fun getProfileData(): UserDataResponse

    @PUT("account/profile")
    suspend fun putProfileData(@Body dataResponse: UserDataResponse)
}