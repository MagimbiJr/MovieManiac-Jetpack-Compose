package com.tana.moviemaniac.screens.allmovies

import androidx.lifecycle.ViewModel
import com.tana.moviemaniac.data.remote.response.movieslist.MoviesList
import com.tana.moviemaniac.repository.Repository
import com.tana.moviemaniac.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllMoviesViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
//    suspend fun getMoviesList(): Resource<MoviesList> {
//        return repository.getMoviesList(0, 0)
//    }
}