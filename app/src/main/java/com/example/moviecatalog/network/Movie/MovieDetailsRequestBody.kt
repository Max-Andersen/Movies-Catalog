package com.example.moviecatalog.network.Movie

@kotlinx.serialization.Serializable
data class MovieDetailsRequestBody (
    val id: String
)