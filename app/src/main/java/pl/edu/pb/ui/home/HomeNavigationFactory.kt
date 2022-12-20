package pl.edu.pb.ui.home

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.edu.pb.common.navigation.NavigationDestination
import pl.edu.pb.common.navigation.NavigationFactory
import pl.edu.pb.ui.home.composable.HomeRoute
import javax.inject.Inject

class HomeNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder) {
        builder.composable(
            route = NavigationDestination.Home.route,
            arguments = NavigationDestination.Home.arguments,
        ) {
            HomeRoute()
        }
    }
}