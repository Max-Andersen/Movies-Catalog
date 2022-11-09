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
        //return favoriteMovieRepository.getFavoriteMovies()


//        val newMovie = MoviePreView()
//        newMovie.id = "0"
//        newMovie.name = "Люцифер"
//        newMovie.country = "США"
//        newMovie.TEMP_IMG = R.drawable.the_magicians
//        newMovie.year = "2022"
//        newMovie.genres = mutableListOf("ЭКШОН", "ПИПЕЦ КРУТО!")
//
//        val movies = mutableListOf(newMovie)
//
//        movies.add(newMovie.copy(id="1"))
//        movies.add(newMovie.copy(id="2"))
//        movies.add(newMovie.copy(id="3"))
//        movies.add(newMovie.copy(id="4"))
//        movies.add(newMovie.copy(id="5"))

       // return movies
    }
}