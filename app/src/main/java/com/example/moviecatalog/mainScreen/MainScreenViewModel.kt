package com.example.moviecatalog.mainScreen

import androidx.lifecycle.ViewModel
import com.example.moviecatalog.R
import com.example.moviecatalog.mainScreen.movie.MoviePreView
import com.example.moviecatalog.repository.MovieRepository

class MainScreenViewModel: ViewModel() {

    val counter = 0
    private val movieRepository = MovieRepository()

    fun getMovie(){
        movieRepository.loadMovie()
    }

    fun getPreView(): MoviePreView{
        val newMovie = MoviePreView()
        newMovie.name = "Люцифер"
        newMovie.country = "США"
        newMovie.TEMP_IMG = R.drawable.lucifer
        newMovie.year = "2022"
        newMovie.genres = mutableListOf("КАША", "ПИПЕЦ")
        return newMovie
    }

}