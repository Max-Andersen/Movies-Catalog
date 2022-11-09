package com.example.moviecatalog.repository

import ReviewRequestBody
import com.example.moviecatalog.network.Network

class ReviewRepository {
    private val reviewApi = Network.getReviewApi()

    suspend fun addReview(movieId: String, reviewRequestBody: ReviewRequestBody){
        reviewApi.addReview(movieId, reviewRequestBody)
    }

    suspend fun editReview(movieId: String, reviewRequestBody: ReviewRequestBody, reviewId: String){
        reviewApi.editReview(movieId, reviewId, reviewRequestBody)
    }

    suspend fun deleteReview(movieId: String, reviewId: String){
        reviewApi.deleteReview(movieId, reviewId)
    }
}