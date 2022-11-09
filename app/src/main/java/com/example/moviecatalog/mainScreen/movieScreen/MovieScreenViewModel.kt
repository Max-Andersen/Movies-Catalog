package com.example.moviecatalog.mainScreen.movieScreen


import com.example.moviecatalog.mainScreen.movieData.Author
import com.example.moviecatalog.mainScreen.movieData.Genres
import com.example.moviecatalog.network.Movie.MovieDetailsResponse
import com.example.moviecatalog.mainScreen.movieData.ReviewsDetails
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.mainScreen.movieData.Movies
import com.example.moviecatalog.repository.FavoriteMoviesRepository
import com.example.moviecatalog.repository.MovieRepository

class MovieScreenViewModel : ViewModel() {
    private val movieRepository: MovieRepository = MovieRepository()
    private val favoriteMoviesRepository: FavoriteMoviesRepository = FavoriteMoviesRepository()

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

    var favoriteMovies: MutableList<String> = mutableListOf()

    suspend fun getFavoriteMovies() {
        for (movie in favoriteMoviesRepository.getFavoriteMovies().movies){
            favoriteMovies.add(movie.id)
        }
    }

    suspend fun loadMovieDetails(id: String) {
        movieData = movieRepository.loadMovieDetails(id)
    }

    suspend fun addToFavorite(id: String) {
        favoriteMoviesRepository.addToFavorites(id)
    }

    suspend fun deleteFromFavorite(id: String) {
        favoriteMoviesRepository.deleteFromFavorites(id)
    }
}
