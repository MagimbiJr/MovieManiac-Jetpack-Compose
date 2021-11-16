package com.tana.moviemaniac.data.remote

import com.tana.moviemaniac.data.remote.response.moviedetail.MovieDetail
import com.tana.moviemaniac.data.remote.response.movieslist.MoviesList
import com.tana.moviemaniac.data.remote.response.suggestions.MoviesSuggestions
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("list_movies.json")
    suspend fun getMoviesList(
        @Query("limit") limit: Int,
        @Query("page_number") pageNumber: Int,
    ): MoviesList

    @GET("movie_details.json")
    suspend fun getMovieDetails(
        @Query("movie_id") id: Int
    ): MovieDetail

    @GET("movie_suggestions.json")
    suspend fun getSuggestions(
        @Query("movie_id") id: Int
    ): MoviesSuggestions

}