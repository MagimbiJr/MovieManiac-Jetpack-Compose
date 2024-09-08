package com.tana.moviemaniac.presentation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.tana.moviemaniac.components.Screens
import com.tana.moviemaniac.presentation.allmovies.AllMoviesListScreen
import com.tana.moviemaniac.presentation.moviedetailscreen.MovieDetailsScreen
import com.tana.moviemaniac.presentation.movielistscreen.MoviesListScreen
import com.tana.moviemaniac.presentation.navigation.Destinations
import com.tana.moviemaniac.presentation.ui.components.MovieBottomNavigation
import kotlinx.coroutines.CoroutineScope

@Composable
fun MovieManiacGraph(
    navHostController: NavHostController,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            MovieBottomNavigation(navHostController = navHostController, onNavigate = { navHostController.navigate(it)})
        }
    ) { paddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = Destinations.HomeGraph,
            modifier = modifier
                .consumeWindowInsets(paddingValues)
        ) {
            navigation<Destinations.HomeGraph>(
                startDestination = Destinations.Home
            ) {
                composable<Destinations.Home> {
                    MoviesListScreen(
                        navHostController = navHostController,
                        coroutineScope = coroutineScope
                    )
                }
                composable<Destinations.Search> {  }
                composable<Destinations.News> {  }
                composable<Destinations.Profile> {  }
            }


            composable(
                "${Screens.DetailScreen.route}/{id}/{movieTitle}",
                arguments = listOf(
                    navArgument(name = "id") {
                        type = NavType.IntType
                    },
                    navArgument(name = "movieTitle") {
                        type = NavType.StringType
                    }
                )
            ) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("id")!!
                val movieTitle = navBackStackEntry.arguments?.getString("movieTitle")!!

                MovieDetailsScreen(id = id, movieTitle = movieTitle, navHostController = navHostController)
            }
            composable(Screens.AllMoviesScreen.route) {
                AllMoviesListScreen(navHostController = navHostController)
            }
        }
    }
}