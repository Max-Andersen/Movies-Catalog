package com.example.moviecatalog.network.Movie

import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("movies/{page}")
    suspend fun getPageOfMovies(@Path("page") page: Int): MoviesPageResponse

    @GET("movies/details/{id}")
    suspend fun getMovieDetails(@Path("id") id: String): MovieDetailsResponse
}