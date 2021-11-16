package com.tana.moviemaniac

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tana.moviemaniac.components.Screens
import com.tana.moviemaniac.screens.allmovies.AllMoviesListScreen
import com.tana.moviemaniac.screens.moviedetailscreen.MovieDetailsScreen
import com.tana.moviemaniac.screens.movielistscreen.MoviesListScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun MovieAniGraph(
    navHostController: NavHostController,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.HomeScreen.route
    ) {
        composable(Screens.HomeScreen.route) {
            MoviesListScreen(
                navHostController = navHostController,
                scaffoldState = scaffoldState,
                coroutineScope = coroutineScope
            )
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