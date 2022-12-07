package pl.edu.pb.ui.favourites

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.edu.pb.common.navigation.NavigationDestination
import pl.edu.pb.common.navigation.NavigationFactory
import javax.inject.Inject

class FavouritesNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder) {
        builder.composable(
            route = NavigationDestination.Favorites.route,
            arguments = NavigationDestination.Favorites.arguments,
        ) {
            FavouritesRoute()
        }
    }
}