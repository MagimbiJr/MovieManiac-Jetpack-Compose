package com.tana.moviemaniac.data.remote.response.moviedetail

data class MovieDetail(
    val meta: Meta,
    val data: Data,
    val status: String,
    val status_message: String
)