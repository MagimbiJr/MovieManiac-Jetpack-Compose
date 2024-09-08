package com.tana.moviemaniac.data

data class MovieListEntry(
    val movieId: Int,
    val movieTitle: String,
    val movieCover: String,
    val year: Int,
    val rating: Double,
    val dateUploaded: String?
)
