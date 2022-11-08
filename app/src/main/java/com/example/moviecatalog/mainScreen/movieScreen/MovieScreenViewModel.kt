package com.example.moviecatalog.mainScreen.movieScreen


import com.example.moviecatalog.mainScreen.movieData.Author
import com.example.moviecatalog.mainScreen.movieData.Genres
import com.example.moviecatalog.network.Movie.MovieDetailsResponse
import com.example.moviecatalog.mainScreen.movieData.ReviewsDetails
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.repository.MovieRepository

class MovieScreenViewModel : ViewModel() {
    private val movieRepository: MovieRepository = MovieRepository()

    var movieData = MovieDetailsResponse(
        "",
        "",
        "",
        0,
        "",
        listOf(Genres("", "")),
        listOf(ReviewsDetails("", 0, "", false, "", Author("", "", ""))),
        0,
        "",
        "",
        "",
        0,
        0,
        0,
    )

    suspend fun loadMovieDetails(id: String) {
        movieData = movieRepository.loadMovieDetails(id)
    }
}
