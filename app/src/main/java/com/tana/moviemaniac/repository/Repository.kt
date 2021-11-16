package com.tana.moviemaniac.repository

import com.tana.moviemaniac.data.remote.MoviesApi
import com.tana.moviemaniac.data.remote.response.moviedetail.MovieDetail
import com.tana.moviemaniac.data.remote.response.movieslist.MoviesList
import com.tana.moviemaniac.data.remote.response.suggestions.MoviesSuggestions
import com.tana.moviemaniac.util.Resource
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: MoviesApi
) {

    suspend fun getMoviesList(paggeNumber: Int, limit: Int): Resource<MoviesList> {
        val response = try {
            api.getMoviesList(pageNumber = paggeNumber, limit = limit)
        } catch (error: Exception) {
            return Resource.Error(message = error.localizedMessage)
        }
        return Resource.Success(data = response)
    }


    suspend fun getMovieDetails(id: Int): Resource<MovieDetail> {
        val response = try {
            api.getMovieDetails(id = id)
        } catch (error: Exception) {
            return Resource.Error(message = error.message.toString())
        }
        return Resource.Success(data = response)
    }

    suspend fun getSuggestions(id: Int): Resource<MoviesSuggestions> {
        val response = try {
            api.getSuggestions(id = id)
        } catch (error: Exception) {
            return Resource.Error(message = error.localizedMessage)
        }
        return Resource.Success(data = response)
    }
}