package com.tana.moviemaniac.presentation.allmovies

import androidx.lifecycle.ViewModel
import com.tana.moviemaniac.repository.Repository
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