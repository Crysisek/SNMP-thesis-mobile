package pl.edu.pb.common.navigation

import androidx.navigation.NamedNavArgument

sealed class NavigationDestination(
    val route: String,
    val arguments: List<NamedNavArgument>,
) {
    object Login : NavigationDestination("login", emptyList())
    object Home : NavigationDestination("home", emptyList())
    object Search : NavigationDestination("search", emptyList())
    object Favorites : NavigationDestination("favorites", emptyList())
    object Settings : NavigationDestination("settings", emptyList())

    companion object {
        val bottomNavigationEntries = listOf( // TODO improve on this
            Home.route,
            Search.route,
            Favorites.route,
            Settings.route,
        )
    }
}
