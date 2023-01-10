package com.example.moviecatalog.repository

import com.example.moviecatalog.network.Movie.MovieDetailsResponse
import com.example.moviecatalog.network.Movie.MoviesPageResponse
import com.example.moviecatalog.network.Movie.MovieApi
import com.example.moviecatalog.network.Network

class MovieRepository {

    private val movieApi: MovieApi = Network.getMovieApi()

    suspend fun loadMovieDetails(idMovie: String): MovieDetailsResponse =
        movieApi.getMovieDetails(idMovie)

    suspend fun loadPageOfMovies(page: Int): MoviesPageResponse = movieApi.getPageOfMovies(page)

}