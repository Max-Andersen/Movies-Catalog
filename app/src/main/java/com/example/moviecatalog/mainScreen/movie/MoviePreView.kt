package com.example.moviecatalog.mainScreen.movie

data class MoviePreView(
    var id: String = "",
    var imgUrl: String = "",

    var TEMP_IMG: Int = 0,

    var name: String = "",
    var year: String = "",
    var country: String = "",
    var genres: MutableList<String> = mutableListOf<String>(),
    var reviews: MutableList<Int> = mutableListOf<Int>()
)