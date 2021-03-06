package com.tana.moviemaniac.screens.movielistscreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.navArgument
import coil.compose.rememberImagePainter
import com.tana.moviemaniac.components.HomeScreenTopBar
import com.tana.moviemaniac.components.LoadingScreen
import com.tana.moviemaniac.components.MovieManiacNavDrawer
import com.tana.moviemaniac.components.Screens
import com.tana.moviemaniac.data.MovieListEntry
import com.tana.moviemaniac.ui.theme.OrangeColor
import com.tana.moviemaniac.util.LIMIT
import kotlinx.coroutines.CoroutineScope

@Composable
fun MoviesListScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    viewModel: MoviesListViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { HomeScreenTopBar(
            scaffoldState = scaffoldState,
            coroutineScope = coroutineScope
        ) },
        drawerContent = { MovieManiacNavDrawer(modifier = modifier)},
        scaffoldState = scaffoldState
    ) {
        val popularMovies by remember { viewModel.popularMovies }
        val trendingMovies by remember { viewModel.trendingMovies }
        val isLoading by remember { viewModel.isLoading }
        val loadError by remember { viewModel.loadError }
        val endReached by remember { viewModel.endReached }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = "Let's download now",
                style = MaterialTheme.typography.h4
            )
            Spacer(modifier = modifier.height(8.dp))
            SearchBar(modifier = modifier) { searchInput ->

            }
            Spacer(modifier = modifier.height(16.dp))
            MoviesList(
                popularMovies = popularMovies,
                trendingMovies = trendingMovies,
                endReached = endReached,
                navHostController = navHostController,
                viewModel = viewModel,
                modifier = modifier,
                errorMessage = loadError,
                isLoading = isLoading
            )

        }
    }
}


@Composable
fun MoviesList(
    popularMovies: List<MovieListEntry>,
    trendingMovies: List<MovieListEntry>,
    endReached: Boolean,
    navHostController: NavHostController,
    viewModel: MoviesListViewModel,
    modifier: Modifier,
    errorMessage: String,
    isLoading: Boolean
) {
    Column {
        PopularMovies(
            movies = popularMovies,
            endReached = endReached,
            isLoading = isLoading,
            modifier = modifier,
            navHostController = navHostController,
            viewModel = viewModel
        )

        Spacer(modifier = modifier.height(12.dp))

        TrendingMovies(
            movies = trendingMovies,
            navHostController = navHostController,
            endReached = endReached,
            viewModel = viewModel
        )
    }
    if (isLoading) {
        LoadingScreen()
    }

    if (errorMessage.isNotBlank()) {
        Box() {
            ErrorScreen(
                message = errorMessage,
                modifier = modifier
            ) {
                viewModel.loadPopularMovies()
            }
        }
    }
}

@Composable
fun PopularMovies(
    movies: List<MovieListEntry>,
    endReached: Boolean,
    isLoading: Boolean,
    modifier: Modifier,
    navHostController: NavHostController,
    viewModel: MoviesListViewModel,
) {
    if (movies.isNotEmpty()) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Popular",
                style = MaterialTheme.typography.body1,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "See all",
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp,
                modifier = modifier
                    .clickable {
                        navHostController.navigate(
                            Screens.AllMoviesScreen.route
                        )
                    }
            )
        }
    }
    Spacer(modifier = modifier.height(12.dp))
    val state = rememberLazyListState()
    LazyRow(
        state = state
    ) {

        itemsIndexed(items = movies) { index, movie ->

            if (index >= movies.lastIndex  && !endReached) {
                    viewModel.loadPopularMovies()
            }
            Column(
                modifier = modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        navHostController.navigate(
                            "${Screens.DetailScreen.route}/${movie.movieId}/${movie.movieTitle}"
                        )
                    }
            ) {
                val painter = rememberImagePainter(data = movie.movieCover)
                Card(
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Image(
                        painter = painter,
                        contentDescription = "Movie Cover",
                        modifier = modifier.size(width = 130.dp, height = 195.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}

@Composable
fun TrendingMovies(
    movies: List<MovieListEntry>,
    endReached: Boolean,
    viewModel: MoviesListViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    if (movies.isNotEmpty()) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Trending movies",
                style = MaterialTheme.typography.body1,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "See all",
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp,
                modifier = modifier
                    .clickable {
                        navHostController.navigate(
                            Screens.AllMoviesScreen.route
                        )
                    }
            )
        }
    }
    Spacer(modifier = modifier.height(12.dp))
    LazyColumn() {
        itemsIndexed(movies) { index, movie ->
            if (index >= movies.lastIndex && !endReached) {
                viewModel.loadTrendingMovies()
            }
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        navHostController.navigate(
                            "${Screens.DetailScreen.route}/${movie.movieId}/${movie.movieTitle}"
                        )
                    },
                shape = RoundedCornerShape(12.dp),
                backgroundColor = MaterialTheme.colors.surface
            ) {
                Row(
                    modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val painter = rememberImagePainter(data = movie.movieCover)
                    Column() {
                        Image(
                            painter = painter,
                            contentDescription = "Movie cover",
                            modifier = modifier
                                .size(width = 60.dp, height = 70.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.FillBounds.also {
                                ContentScale.Fit
                            }
                        )
                    }
                    Spacer(modifier = modifier.width(8.dp))
                    Column(
                        modifier = modifier.weight(1f)
                    ) {
                        Text(
                            text = "${movie.movieTitle} - ${movie.year}",
                            style = MaterialTheme.typography.body1,
                            modifier = modifier,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Ratings",
                                modifier = modifier
                                    .size(16.dp),
                                tint = OrangeColor
                            )
                            Spacer(modifier = modifier.width(4.dp))
                            Text(
                                text = movie.rating.toString()
                            )
                        }
                    }
                    Spacer(modifier = modifier.width(8.dp))
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Card(
                            modifier = modifier
                                .clip(CircleShape)
                                .clickable {
                                    navHostController.navigate(
                                        "${Screens.DetailScreen.route}/${movie.movieId}/${movie.movieTitle}"
                                    )
                                },
                            shape = CircleShape
                        ) {
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = "Next",
                                modifier = modifier.padding(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier,
    onSearch: (String) -> Unit,

    ) {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = {
            text = it
            onSearch(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .clip(CircleShape),
        placeholder = { Text(text = "Search") },
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search") },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = MaterialTheme.colors.onSurface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = MaterialTheme.colors.surface
        )
    )
}

@Composable
fun ErrorScreen(
    message: String,
    modifier: Modifier,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.body1,
            fontSize = 18.sp,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Center

        )
        Spacer(modifier = modifier.height(16.dp))
        Button(
            onClick = { onRetry() },
            shape = CircleShape
        ) {
            Text(
                text = "Retry",
                modifier = modifier.padding(horizontal = 32.dp, vertical = 8.dp)
            )
        }
    }
}