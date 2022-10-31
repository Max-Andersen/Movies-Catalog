package com.example.moviecatalog.mainScreen.movieData

data class Genre(
    var id: String = "",
    var name: String = "",
)


data class MovieDetails(
    var time: Int = 0,
    var tagline: String = "",
    var description: String = "",
    var director: String = "",
    var budget: Int = 0,
    var fees: Int = 0,
    var ageLimit: Int = 0,
    var reviews: MutableList<Review> = mutableListOf(),
    var filmId: String = "",
    var name: String = "",
    var poster: String = "",
    var year: String = "",
    var country: String = "",
    var genres: MutableList<Genre> = mutableListOf()
)