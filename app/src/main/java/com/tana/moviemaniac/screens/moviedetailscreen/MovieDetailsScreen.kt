package com.tana.moviemaniac.screens.moviedetailscreen

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.textservice.SuggestionsInfo
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.tana.moviemaniac.components.DetailScreenTopBar
import com.tana.moviemaniac.components.LoadingScreen
import com.tana.moviemaniac.components.Screens
import com.tana.moviemaniac.data.Suggestion
import com.tana.moviemaniac.data.remote.response.moviedetail.MovieDetail
import com.tana.moviemaniac.data.remote.response.suggestions.MoviesSuggestions
import com.tana.moviemaniac.ui.theme.OrangeColor
import com.tana.moviemaniac.util.Resource
import java.net.URLEncoder

@Composable
fun MovieDetailsScreen(
    id: Int,
    movieTitle: String,
    navHostController: NavHostController,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { DetailScreenTopBar(navHostController = navHostController)}
    ) {
        val movieInfo = produceState<Resource<MovieDetail>>(initialValue = Resource.Loading()) {
            value = viewModel.getMovieInfo(id)
        }.value
        viewModel.movieId = id

        MovieDetailStateWrapper(
            movieInfo = movieInfo,
            viewModel = viewModel,
            navHostController = navHostController
        )
    }
}

@Composable
fun MovieDetailStateWrapper(
    movieInfo: Resource<MovieDetail>,
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModel,
    navHostController: NavHostController,
    loadingModifier: Modifier = Modifier
) {
    when (movieInfo) {
        is Resource.Success -> {
            MovieDetails(
                movieDetail = movieInfo.data!!,
                viewModel = viewModel,
                navHostController = navHostController
            )
        }
        is Resource.Error -> {
            Text(
                text = movieInfo.message!!,
                modifier = modifier
            )
        }
        is Resource.Loading -> {
            LoadingScreen(modifier = loadingModifier)
        }
    }
}



@Composable
fun MovieDetails(
    movieDetail: MovieDetail,
    viewModel: MovieDetailViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val painter = rememberImagePainter(data = movieDetail.data.movie.large_cover_image)
    val openDialog = remember { mutableStateOf(false) }
    val suggestions = remember { viewModel.suggestions.value }

    Column() {
        Image(
            painter = painter,
            contentDescription = "Movie Cover",
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .verticalScroll(scrollState)
        ) {
            Card(
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = modifier.fillMaxWidth(0.60f)
                    ) {
                        Text(
                            text = movieDetail.data.movie.title,
                            style = MaterialTheme.typography.body1,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rating",
                                modifier = modifier
                                    .size(16.dp),
                                tint = OrangeColor
                            )
                            Text(text = movieDetail.data.movie.rating.toString())
                        }
                    }
                    val context = LocalContext.current
                    Column() {
                        Button(
                            onClick = {
                                openDialog.value = true
                            },
                            shape = CircleShape
                        ) {
                            Text(
                                text = "Download",
                                modifier = modifier
                                    .padding(horizontal = 16.dp, vertical = 6.dp)
                            )
                        }

                        if (openDialog.value) {
                            DownloadOptionsDialog(
                                openDialog = openDialog,
                                on720clicked = {

                                    val torrent = movieDetail.data.movie.torrents[0]
                                    val encodedMovieName = URLEncoder.encode(movieDetail.data.movie.title, "utf-8")

                                   val magnetUri = Uri.parse(
                                       "magnet:?xt=urn:btih:${torrent.hash}&dn=$encodedMovieName&tr=udp://open.demonii.com:1337/announce" +
                                           "&tr=udp://tracker.openbittorrent.com:80&tr=udp://tracker.coppersurfer.tk:6969&tr=udp://glotorrents.pw:6969/announce" +
                                           "&udp://tracker.opentrackr.org:1337/announce&tr=udp://torrent.gresille.org:80/announce&udp://p4p.arenabg.com:1337" +
                                               "&tr=udp://p4p.arenabg.com:1337"
                                   )
                                    Log.d("Magnetic URI", magnetUri.toString())
                                    val intent = Intent(Intent.ACTION_VIEW, magnetUri)
                                    context.startActivity(intent)
                                    openDialog.value = false
                                },
                                on1080Clicked = {
                                    val torrent = movieDetail.data.movie.torrents[1]
                                    val encodedMovieName = URLEncoder.encode(movieDetail.data.movie.title, "utf-8")
                                    val magnetUrl = Uri.parse(
                                        "magnet:?xt=urn:btih:${torrent.hash}&dn=$encodedMovieName&tr=udp://open.demonii.com:1337/announce" +
                                                "&tr=udp://tracker.openbittorrent.com:80&tr=udp://tracker.coppersurfer.tk:6969&tr=udp://glotorrents.pw:6969/announce" +
                                                "&udp://tracker.opentrackr.org:1337/announce&tr=udp://torrent.gresille.org:80/announce&udp://p4p.arenabg.com:1337" +
                                                "&tr=udp://p4p.arenabg.com:1337"
                                    )
                                    val intent = Intent(Intent.ACTION_VIEW, magnetUrl)
                                    context.startActivity(intent)
                                    openDialog.value = false
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = modifier.height(16.dp))
            GenresList(genres = movieDetail.data.movie.genres)
            Spacer(modifier = modifier.height(16.dp))
            Text(
                text = "Overview",
                style = MaterialTheme.typography.body1,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = modifier.height(12.dp))
            Text(
                text = movieDetail.data.movie.description_full,
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )
            Spacer(modifier = modifier.height(16.dp))
            Suggestions(
                suggestions = suggestions,
                modifier = modifier,
                navHostController = navHostController
            )
        }
    }
}

@Composable
fun Suggestions(
    suggestions: List<Suggestion>,
    modifier: Modifier,
    navHostController: NavHostController
) {
    if (suggestions.isNotEmpty()) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Related",
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
    LazyRow() {
        item(suggestions) {
            suggestions.forEachIndexed { index, suggestion ->
                Column(
                    modifier = modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            navHostController.navigate(
                                "${Screens.DetailScreen.route}/${suggestion.movieId}/${suggestion.title}"
                            )
                        }
                ) {
                    val painter = rememberImagePainter(data = suggestion.movieCover)
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
}

@Composable
fun GenresList(
    genres: List<String>,
    modifier: Modifier = Modifier
) {
    LazyRow() {
        item(genres) {
            genres.forEach { genre ->
                Card(
                    modifier = modifier
                        .padding(end = 16.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colors.onSurface,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    shape = RoundedCornerShape(16.dp),
                    backgroundColor = MaterialTheme.colors.background
                ) {
                    Text(
                        text = genre,
                        style = MaterialTheme.typography.body1,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DownloadOptionsDialog(
    openDialog: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    on720clicked: () -> Unit,
    on1080Clicked: () -> Unit
) {
    Column() {
        Dialog(
            onDismissRequest = { openDialog.value = false }
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colors.background)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "720p",
                        style = MaterialTheme.typography.body1,
                        modifier = modifier
                            .fillMaxWidth()
                            .clickable { on720clicked() }
                            .padding(top = 16.dp, bottom = 8.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = modifier.height(4.dp))
                    Text(
                        text = "1080p",
                        style = MaterialTheme.typography.body1,
                        modifier = modifier
                            .fillMaxWidth()
                            .clickable { on1080Clicked() }
                            .padding(top = 8.dp, bottom = 16.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}