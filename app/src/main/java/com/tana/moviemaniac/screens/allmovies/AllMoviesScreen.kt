package com.tana.moviemaniac.screens.allmovies

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.tana.moviemaniac.components.AllMoviesScreenTopBar
import com.tana.moviemaniac.components.LoadingScreen
import com.tana.moviemaniac.components.Screens
import com.tana.moviemaniac.data.MovieListEntry
import com.tana.moviemaniac.screens.movielistscreen.MoviesListViewModel
import com.tana.moviemaniac.util.LIMIT

@Composable
fun AllMoviesListScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MoviesListViewModel = hiltViewModel()
) {
    val verticalScrollState = rememberScrollState()
    val movies by remember { viewModel.allMovies }
    val isLoading by remember { viewModel.isLoading }
    val loadError by remember { viewModel.loadError }

   Scaffold(
       topBar = { AllMoviesScreenTopBar(navHostController = navHostController)}
   ) { paddingValues ->
       Column {
           if (isLoading) {
               LoadingScreen()
           } else {
               AllMoviesList(
                   movies = movies,
                   navHostController = navHostController,
                   modifier = modifier,
                   verticalScrollState = verticalScrollState,
                   viewModel = viewModel
               )
           }
       }
   }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllMoviesList(
    movies: List<MovieListEntry>,
    modifier: Modifier,
    verticalScrollState: ScrollState,
    viewModel: MoviesListViewModel,
    navHostController: NavHostController
) {
    StaggeredVerticalGrid(
        modifier = modifier
            .verticalScroll(verticalScrollState)
    ) {
        var position = 0
        val mappedMovies = movies.mapIndexed { index, movie ->
            position = index
            MovieListEntry(
                movieId = movie.movieId,
                movieTitle = movie.movieTitle,
                movieCover = movie.movieCover,
                year = movie.year,
                rating = movie.rating,
                dateUploaded = movie.dateUploaded
            )
        }

        viewModel.onChangeScrollPos(position = position)

        val page = viewModel.pageNumber
        if ( position + 1 >= page * LIMIT ) {
            viewModel.nextPage()
        }
        mappedMovies.forEachIndexed { index,  movie ->
            position = index
            val painter = rememberImagePainter(data = movie.movieCover)
            Card(
                modifier = modifier
                    .padding(start = 4.dp, end = 4.dp, bottom = 8.dp)
                    .clickable {
                        navHostController.navigate(
                            "${Screens.DetailScreen.route}/${movie.movieId}/${movie.movieTitle}"
                        )
                    }
            ) {
                Column() {
                    Image(
                        painter = painter,
                        contentDescription = "Movie cover",
                        modifier = modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = movie.movieTitle,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    numColumns: Int = 2,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->

        val columnWidth = constraints.maxWidth / numColumns
        val itemConstraint = constraints.copy(maxWidth = columnWidth)

        var columnHeights = IntArray(numColumns) { 0 }
        val placeables = measurables.map { measurable ->
            val column = shortestColumn(columnHeights)
            val placeable = measurable.measure(itemConstraint)
            columnHeights[column] += placeable.height
            placeable
        }

        val heights = columnHeights.maxOrNull()
            ?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight

        layout(
            width = constraints.maxWidth,
            height = heights
        ) {
            val columnYPointer = IntArray(numColumns) { 0 }

            placeables.forEach { placeable ->
                val column = shortestColumn(columnYPointer)
                placeable.place(
                    x = columnWidth * column,
                    y = columnYPointer[column]
                )
                columnYPointer[column] += placeable.height
            }
        }
    }
}

private fun shortestColumn(columnHeights: IntArray): Int {
    var minHeights = Int.MAX_VALUE
    var columnIndex = 0

    columnHeights.forEachIndexed { index, height ->
        if (height < minHeights) {
            minHeights = height
            columnIndex = index
        }
    }
    return columnIndex
}


