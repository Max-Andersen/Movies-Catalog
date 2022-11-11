package com.example.moviecatalog.mainScreen.movieScreen


import ReviewRequestBody
import com.example.moviecatalog.mainScreen.movieData.Author
import com.example.moviecatalog.mainScreen.movieData.Genres
import com.example.moviecatalog.network.Movie.MovieDetailsResponse
import com.example.moviecatalog.mainScreen.movieData.ReviewsDetails
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.mainScreen.movieData.Movies
import com.example.moviecatalog.repository.FavoriteMoviesRepository
import com.example.moviecatalog.repository.MovieRepository
import com.example.moviecatalog.repository.ReviewRepository
import com.example.moviecatalog.repository.UserRepository
import kotlinx.coroutines.flow.collect
import javax.sql.StatementEvent

class MovieScreenViewModel : ViewModel() {
    private val movieRepository: MovieRepository = MovieRepository()
    private val favoriteMoviesRepository: FavoriteMoviesRepository = FavoriteMoviesRepository()
    private val userRepository: UserRepository = UserRepository()
    private val reviewRepository: ReviewRepository = ReviewRepository()

    var movieData: MovieDetailsResponse? = null

    var favoriteMovies: MutableList<String> = mutableListOf()

    var myId = ""

    var myReview: ReviewsDetails? = null

    suspend fun getMyId(){
        userRepository.getData().collect{
            it.onSuccess { data ->
                myId = data.id
            }
        }
    }

    suspend fun getFavoriteMovies() {
        for (movie in favoriteMoviesRepository.getFavoriteMovies().movies){
            favoriteMovies.add(movie.id)
        }
    }

    suspend fun loadMovieDetails(id: String) {
        movieData = movieRepository.loadMovieDetails(id)
    }

    fun getMyReview(){
        for (review in movieData!!.reviews) {
            if (review.author != null){
                if (review.author.userId == myId) {
                    myReview = review
                    movieData!!.reviews.toMutableList().remove(review)
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

    suspend fun addReview(movieId: String, reviewText: String, rating: Int, isAnonymous: Boolean){
        reviewRepository.addReview(movieId, ReviewRequestBody(reviewText.ifEmpty { "ㅤ" }, rating, isAnonymous))
        loadMovieDetails(movieId)
    }

    suspend fun editReview(movieId: String, reviewText: String, rating: Int, isAnonymous: Boolean, reviewId: String){
        reviewRepository.editReview(movieId, ReviewRequestBody(reviewText.ifEmpty { "ㅤ" }, rating, isAnonymous), reviewId)
        loadMovieDetails(movieId)
    }

    suspend fun deleteReview(movieId: String, reviewId: String){
        reviewRepository.deleteReview(movieId, reviewId)
    }

}
