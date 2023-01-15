package com.example.moviecatalog.mainScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviecatalog.mainScreen.movieData.MovieSource
import com.example.moviecatalog.mainScreen.movieData.Movies
import com.example.moviecatalog.repository.FavoriteMoviesRepository
import com.example.moviecatalog.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MainScreenViewModel() : ViewModel() {

    private val movieRepository: MovieRepository = MovieRepository()

    private val favoriteMoviesRepository: FavoriteMoviesRepository = FavoriteMoviesRepository()

    val movies: Flow<PagingData<Movies>> = Pager(PagingConfig(pageSize = 6)) {
        MovieSource(movieRepository)
    }.flow

    var favoriteMovies = mutableStateListOf<Movies>()

    lateinit var promotedFilm: Movies

    suspend fun getFavoriteMovies() {
        val noLongerFavoriteFilms = favoriteMovies.slice(0 until favoriteMovies.size).toMutableList()
        favoriteMoviesRepository.getFavoriteMovies().movies.forEach { movie ->
            if (movie != promotedFilm && !favoriteMovies.contains(movie)){
                favoriteMovies.add(movie)
                noLongerFavoriteFilms.remove(movie)
            }
            if (favoriteMovies.contains(movie)){
                noLongerFavoriteFilms.remove(movie)
            }
        }
        noLongerFavoriteFilms.forEach { movie ->
            favoriteMovies.remove(movie)
        }
    }

    suspend fun deleteFromFavoriteMovies(movie: Movies){
        favoriteMoviesRepository.deleteFromFavorites(movie.id)
        favoriteMovies.remove(movie)
    }
}
