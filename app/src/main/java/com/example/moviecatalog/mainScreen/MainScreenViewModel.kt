package com.example.moviecatalog.mainScreen

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviecatalog.R
import com.example.moviecatalog.mainScreen.movieData.MoviePreView
import com.example.moviecatalog.mainScreen.movieData.MovieSource
import com.example.moviecatalog.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MainScreenViewModel : ViewModel() {

    val counter = 0
    private val movieRepository = MovieRepository()

    val movies: Flow<PagingData<MoviePreView>> = Pager(PagingConfig(pageSize = 6)) {
        MovieSource(movieRepository)
    }.flow

    fun getMovie() {
        //movieRepository.loadPageOfMovies(1)
    }

    fun getPreView(): MoviePreView {
        val newMovie = MoviePreView()
        newMovie.name = "Люцифер"
        newMovie.country = "США"
        newMovie.TEMP_IMG = R.drawable.lucifer
        newMovie.year = "2022"
        newMovie.genres = mutableListOf("ЭКШОН", "ПИПЕЦ КРУТО!")
        return newMovie
    }

    fun getFavouriteMovies(): List<MoviePreView> {

        val newMovie = MoviePreView()
        newMovie.id = "0"
        newMovie.name = "Люцифер"
        newMovie.country = "США"
        newMovie.TEMP_IMG = R.drawable.the_magicians
        newMovie.year = "2022"
        newMovie.genres = mutableListOf("ЭКШОН", "ПИПЕЦ КРУТО!")

        val movies = mutableListOf(newMovie)

        movies.add(newMovie.copy(id="1"))
        movies.add(newMovie.copy(id="2"))
        movies.add(newMovie.copy(id="3"))
        movies.add(newMovie.copy(id="4"))
        movies.add(newMovie.copy(id="5"))

        return movies
    }

}