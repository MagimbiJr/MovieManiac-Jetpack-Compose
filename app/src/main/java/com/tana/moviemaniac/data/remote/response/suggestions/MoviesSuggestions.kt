package com.tana.moviemaniac.data.remote.response.suggestions

data class MoviesSuggestions(
    val meta: Meta,
    val `data`: Data,
    val status: String,
    val status_message: String
)