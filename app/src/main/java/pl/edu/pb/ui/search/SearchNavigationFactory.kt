package pl.edu.pb.ui.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.edu.pb.common.navigation.NavigationDestination
import pl.edu.pb.common.navigation.NavigationFactory
import pl.edu.pb.ui.search.composable.SearchRoute
import javax.inject.Inject

class SearchNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder) {
        builder.composable(
            route = NavigationDestination.Search.route,
            arguments = NavigationDestination.Search.arguments,
        ) {
            SearchRoute()
        }
    }
}