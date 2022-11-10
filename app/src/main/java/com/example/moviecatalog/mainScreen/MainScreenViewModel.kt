package com.example.moviecatalog.mainScreen

import com.example.moviecatalog.mainScreen.movieData.Movies
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviecatalog.mainScreen.movieData.MovieSource
import com.example.moviecatalog.repository.FavoriteMoviesRepository
import com.example.moviecatalog.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MainScreenViewModel() : ViewModel() {

    private val movieRepository: MovieRepository = MovieRepository()

    private val favoriteMoviesRepository: FavoriteMoviesRepository = FavoriteMoviesRepository()

    val movies: Flow<PagingData<Movies>> = Pager(PagingConfig(pageSize = 6)) {
        MovieSource(movieRepository)
    }.flow

    var favoriteMovies: List<Movies> = listOf()

    suspend fun getFavoriteMovies() {
        favoriteMovies = favoriteMoviesRepository.getFavoriteMovies().movies
    }

    suspend fun deleteFromFavoriteMovies(movieId: String){
        favoriteMoviesRepository.deleteFromFavorites(movieId)
    }
}