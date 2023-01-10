package com.example.moviecatalog.repository

import ListOfMovies
import com.example.moviecatalog.network.Network

class FavoriteMoviesRepository {

    private val favoriteMoviesApi = Network.getFavoriteMoviesApi()

    suspend fun getFavoriteMovies(): ListOfMovies = favoriteMoviesApi.getFavoriteMovies()

    suspend fun addToFavorites(id: String) {
        favoriteMoviesApi.addToFavorite(id)
    }

    suspend fun deleteFromFavorites(id: String) {
        favoriteMoviesApi.deleteFromFavorites(id)
    }

}