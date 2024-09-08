package com.tana.moviemaniac.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tana.moviemaniac.R
import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    data object HomeGraph : Destinations()
    @Serializable
    data object Home : Destinations()
    @Serializable
    data object Search : Destinations()
    @Serializable
    data object News : Destinations()
    @Serializable
    data object Profile : Destinations()
}

@Serializable
data class MovieDetails(val id: Int, val movieTitle: String)

enum class BottomNavigation(val route: Destinations, @DrawableRes val icon: Int, @StringRes val label: Int) {
    Home(route = Destinations.Home, icon = R.drawable.home_outlined_icon, label = R.string.home),
    Search(route = Destinations.Search, icon = R.drawable.search_icon, label = R.string.search),
    News(route = Destinations.News, icon = R.drawable.world_map_icon, label = R.string.news),
    Profile(route = Destinations.Profile, icon = R.drawable.profile_outlined_icon, label = R.string.profile)
}