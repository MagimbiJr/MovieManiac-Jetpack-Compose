package com.tana.moviemaniac.presentation.movielistscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tana.moviemaniac.data.MovieListEntry
import com.tana.moviemaniac.repository.Repository
import com.tana.moviemaniac.util.LIMIT
import com.tana.moviemaniac.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var pageNumber = 1
    var sortBy: Any? = "rating"
    var popularMovies = mutableStateOf<List<MovieListEntry>>(listOf())
    var trendingMovies = mutableStateOf<List<MovieListEntry>>(listOf())
    var allMovies = mutableStateOf<List<MovieListEntry>>(listOf())
    var isLoading = mutableStateOf(false)
    var loadError = mutableStateOf("")
    var endReached = mutableStateOf(false)
    private var scrollPos = 0


    init {
        loadPopularMovies()
        loadTrendingMovies()
        loadAllMovies()
    }

    fun loadPopularMovies() {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = repository.getMoviesList(paggeNumber = pageNumber, limit = LIMIT)) {
                is Resource.Success -> {
                    endReached.value = pageNumber * LIMIT >= result.data!!.data.movie_count
                    val moviesListEntry = result.data.data.movies.map { movie ->
                        sortBy = movie.rating
                        MovieListEntry(
                            movieId = movie.id,
                            movieTitle = movie.title,
                            movieCover = movie.medium_cover_image,
                            year = movie.year,
                            rating = movie.rating,
                            dateUploaded = movie.date_uploaded
                        )
                    }

                    pageNumber++
                    loadError.value = ""
                    isLoading.value = false
                    popularMovies.value += moviesListEntry

                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading -> Unit
            }

        }
    }

    fun loadTrendingMovies() {
        viewModelScope.launch {
            isLoading.value = true
            when(val result = repository.getMoviesList(paggeNumber = pageNumber,limit = LIMIT)) {
                is Resource.Success -> {
                    endReached.value = pageNumber * LIMIT >= result.data!!.data.movie_count
                    val moviesListEntry = result.data.data.movies.map { movie ->
                        //moviesList.value = result.data.data.movies.mapIndexed { index, movie ->
                        sortBy = movie.date_uploaded
                        MovieListEntry(
                            movieId = movie.id,
                            movieTitle = movie.title,
                            movieCover = movie.medium_cover_image,
                            year = movie.year,
                            rating = movie.rating,
                            dateUploaded = movie.date_uploaded
                        )
                    }

                    pageNumber++
                    //Log.d(TAG, "PageNo: $pageNumber")
                    loadError.value = ""
                    isLoading.value = false
                    trendingMovies.value += moviesListEntry

                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading -> Unit
            }

        }
    }

    private fun loadAllMovies() {
        viewModelScope.launch {
            isLoading.value = true
            when(val result = repository.getMoviesList(paggeNumber = pageNumber,limit = LIMIT)) {
                is Resource.Success -> {
                    endReached.value = pageNumber * LIMIT >= result.data!!.data.movie_count
                    val moviesListEntry = result.data.data.movies.map { movie ->
                        //moviesList.value = result.data.data.movies.mapIndexed { index, movie ->
                        sortBy = movie.date_uploaded
                        MovieListEntry(
                            movieId = movie.id,
                            movieTitle = movie.title,
                            movieCover = movie.medium_cover_image,
                            year = movie.year,
                            rating = movie.rating,
                            dateUploaded = movie.date_uploaded
                        )
                    }

                    pageNumber++
                    loadError.value = ""
                    isLoading.value = false
                    allMovies.value += moviesListEntry

                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading -> Unit
            }

        }
    }

    fun nextPage() {
        viewModelScope.launch {
            if ((scrollPos + 1) >= pageNumber * LIMIT) {
                incrementPageNumber()
                delay(1000)

                if (pageNumber > 1) {
                    val result = repository.getMoviesList(paggeNumber = pageNumber, limit = LIMIT)
                    //endReached.value = pageNumber * LIMIT >= result.data!!.data.movie_count
                    val moviesListEntry = result.data!!.data.movies.mapIndexed { index, movie ->
                        //moviesList.value = result.data.data.movies.mapIndexed { index, movie ->
                        MovieListEntry(
                            movieId = movie.id,
                            movieTitle = movie.title,
                            movieCover = movie.medium_cover_image,
                            year = movie.year,
                            rating = movie.rating,
                            dateUploaded = movie.date_uploaded
                        )
                    }
                    loadError.value = ""
                    isLoading.value = false

                    appendMovies(movies = moviesListEntry)
                }
            }
        }
    }

    private fun incrementPageNumber() {
        pageNumber++
    }

    fun onChangeScrollPos(position: Int) {
        scrollPos = position
    }

    private fun appendMovies(movies: List<MovieListEntry>) {
        var currentMovies = ArrayList(popularMovies.value)
        currentMovies.addAll(movies)
        popularMovies.value = currentMovies

    }

}