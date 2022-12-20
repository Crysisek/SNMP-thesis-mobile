package pl.edu.pb.common.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavigationDestination(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
) {
    object Login : NavigationDestination("login")
    object Home : NavigationDestination("home")
    object Search : NavigationDestination("search")
    object Favorites : NavigationDestination("favorites")
    object Settings : NavigationDestination("settings")
    object ClientDetails : NavigationDestination(
        route = "client_details/{$CLIENT_ID_PARAM}",
        arguments = listOf(navArgument(CLIENT_ID_PARAM) { type = NavType.StringType }),
    ) {
        const val CLIENT_ID: String = CLIENT_ID_PARAM
        fun createClientDetailsRoute(clientId: String) = route.replace(
            "{$CLIENT_ID}",
            clientId,
        )
    }

    companion object {
        val bottomNavigationEntries = listOf(
            // TODO improve on this
            Home.route,
            Search.route,
            Favorites.route,
            Settings.route,
        )
        private const val CLIENT_ID_PARAM = "client_id"
    }
}
