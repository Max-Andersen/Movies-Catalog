package com.example.moviecatalog.network.FavoriteMovies

import ListOfMovies
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteMoviesApi {

    @GET("favorites")
    suspend fun getFavoriteMovies(): ListOfMovies

    @POST("favorites/{id}/add")
    suspend fun addToFavorite(@Path("id") id: String)

    @DELETE("favorites/{id}/delete")
    suspend fun deleteFromFavorites(@Path("id") id: String)

}