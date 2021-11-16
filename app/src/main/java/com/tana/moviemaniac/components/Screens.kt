package com.tana.moviemaniac.components

sealed class Screens(val route: String) {
    object HomeScreen : Screens("movies_list_screen")
    object DetailScreen : Screens("detail_screen")
    object AllMoviesScreen: Screens("all_movies_screen")
    object ProfileScreen: Screens("profile_screen")
}
