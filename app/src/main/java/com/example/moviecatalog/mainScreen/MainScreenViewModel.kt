package com.example.moviecatalog.mainScreen

import androidx.lifecycle.ViewModel
import com.example.moviecatalog.repository.MovieRepository

class MainScreenViewModel: ViewModel() {

    val counter = 0
    private val movieRepository = MovieRepository()

    fun getMovie(){
        movieRepository.loadMovie()
    }

}