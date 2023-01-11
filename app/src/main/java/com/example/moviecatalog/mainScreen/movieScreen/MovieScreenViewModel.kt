package com.example.moviecatalog.mainScreen.movieScreen

import ReviewRequestBody
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.mainScreen.movieData.ReviewsDetails
import com.example.moviecatalog.network.Movie.MovieDetailsResponse
import com.example.moviecatalog.network.Network
import com.example.moviecatalog.repository.FavoriteMoviesRepository
import com.example.moviecatalog.repository.MovieRepository
import com.example.moviecatalog.repository.ReviewRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieScreenViewModel : ViewModel() {
    private val movieRepository: MovieRepository = MovieRepository()
    private val favoriteMoviesRepository: FavoriteMoviesRepository = FavoriteMoviesRepository()
    private val reviewRepository: ReviewRepository = ReviewRepository()

    lateinit var movieData: MovieDetailsResponse

    var favoriteMovies: MutableList<String> = mutableListOf()

    var myId = Network.userId

    var myReview: ReviewsDetails? = null

    fun getData(id: String): Flow<MovieDetailsResponse> {
        return flow {
            movieData = movieRepository.loadMovieDetails(id)
            getFavoriteMovies()
            getMyReview()
            emit(movieData)
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun getFavoriteMovies() {
        for (movie in favoriteMoviesRepository.getFavoriteMovies().movies) {
            favoriteMovies.add(movie.id)
        }
    }

    private fun getMyReview() {
        for (review in movieData.reviews) {
            if (review.author != null) {
                if (review.author.userId == myId) {
                    myReview = review
                    movieData.reviews.toMutableList().remove(review)
                    break
                }
            }
        }
    }

    suspend fun addToFavorite(id: String) {
        favoriteMoviesRepository.addToFavorites(id)
    }

    suspend fun deleteFromFavorite(id: String) {
        favoriteMoviesRepository.deleteFromFavorites(id)
    }

    suspend fun addReview(movieId: String, reviewText: String, rating: Int, isAnonymous: Boolean) {
        reviewRepository.addReview(
            movieId,
            ReviewRequestBody(reviewText.ifEmpty { "ㅤ" }, rating, isAnonymous)
        )
    }

    suspend fun editReview(
        movieId: String,
        reviewText: String,
        rating: Int,
        isAnonymous: Boolean,
        reviewId: String
    ) {
        reviewRepository.editReview(
            movieId,
            ReviewRequestBody(reviewText.ifEmpty { "ㅤ" }, rating, isAnonymous),
            reviewId
        )
    }

    suspend fun deleteReview(movieId: String, reviewId: String) {
        reviewRepository.deleteReview(movieId, reviewId)
    }
}
