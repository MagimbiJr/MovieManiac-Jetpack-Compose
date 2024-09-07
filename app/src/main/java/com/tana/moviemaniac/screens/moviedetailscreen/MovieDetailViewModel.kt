package com.tana.moviemaniac.screens.moviedetailscreen

import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tana.moviemaniac.data.Suggestion
import com.tana.moviemaniac.data.remote.response.moviedetail.MovieDetail
import com.tana.moviemaniac.data.remote.response.suggestions.MoviesSuggestions
import com.tana.moviemaniac.repository.Repository
import com.tana.moviemaniac.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var suggestions = mutableStateOf(listOf<Suggestion>())
    var movieId: Int = 0

    suspend fun getMovieInfo(id: Int): Resource<MovieDetail> {
        return repository.getMovieDetails(id = id)
    }
}