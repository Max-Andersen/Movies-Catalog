package com.example.moviecatalog.network.Movie

@kotlinx.serialization.Serializable
data class MovieListRequestBody (
    val page: Int
)