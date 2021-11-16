package com.tana.moviemaniac.data.remote.response.movieslist

data class Data(
    val limit: Int,
    val movie_count: Int,
    val movies: List<Movy>,
    val page_number: Int
)