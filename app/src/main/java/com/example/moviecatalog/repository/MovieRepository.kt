package com.example.moviecatalog.repository

import com.example.moviecatalog.R
import com.example.moviecatalog.mainScreen.movieData.MoviePreView

class MovieRepository {

    fun loadMovieDetails(idMovie: String){

    }

    fun loadPageOfMovies(page: Int): List<MoviePreView> {

        val newFilm = MoviePreView()
        newFilm.TEMP_IMG = R.drawable.lucifer
        newFilm.country = "США"
        newFilm.year = "1999"
        newFilm.name = "Люцифер"
        newFilm.genres = mutableListOf("драма", "криминал")
        //TODO(добавить ID)

        return mutableListOf(newFilm, newFilm, newFilm, newFilm, newFilm)
    }
}