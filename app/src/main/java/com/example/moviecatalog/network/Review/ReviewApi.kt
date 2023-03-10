package com.example.moviecatalog.network.Review

import ReviewRequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReviewApi {

    @POST("movie/{movieId}/review/add")
    suspend fun addReview(@Path("movieId") movieId: String, @Body body: ReviewRequestBody)

    @PUT("movie/{movieId}/review/{id}/edit")
    suspend fun editReview(@Path("movieId") movieId: String, @Path("id") id: String, @Body body: ReviewRequestBody)

    @DELETE("movie/{movieId}/review/{id}/delete")
    suspend fun deleteReview(@Path("movieId") movieId: String, @Path("id") id: String)
}