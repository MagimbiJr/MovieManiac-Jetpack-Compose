package com.tana.moviemaniac.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreenTopBar(
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope
) {
    TopAppBar(
        title = { Text(text = "Movie Maniac") },
        navigationIcon = {
            IconButton(onClick = {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        backgroundColor = MaterialTheme.colors.background
    )
}

@Composable
fun DetailScreenTopBar(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = "") },
        navigationIcon = {
            IconButton(onClick = { navHostController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "",
                    modifier = modifier.size(16.dp)
                )
            }
        },
        backgroundColor = MaterialTheme.colors.background
    )
}

@Composable
fun AllMoviesScreenTopBar(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = "Movies") },
        navigationIcon = {
            IconButton(onClick = { navHostController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "",
                    modifier = modifier.size(16.dp)
                )
            }
        },
        backgroundColor = MaterialTheme.colors.background
    )
}