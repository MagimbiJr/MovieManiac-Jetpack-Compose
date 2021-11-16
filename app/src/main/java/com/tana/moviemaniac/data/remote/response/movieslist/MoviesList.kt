package com.tana.moviemaniac.data.remote.response.movieslist

data class MoviesList(
    val meta: Meta,
    val `data`: Data,
    val status: String,
    val status_message: String
)