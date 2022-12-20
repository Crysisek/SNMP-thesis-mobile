package pl.edu.pb.ui.clientdetails

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.edu.pb.common.navigation.NavigationDestination
import pl.edu.pb.common.navigation.NavigationFactory
import pl.edu.pb.ui.clientdetails.composable.ClientDetailsRoute
import javax.inject.Inject

class ClientDetailsNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder) {
        builder.composable(
            route = NavigationDestination.ClientDetails.route,
            arguments = NavigationDestination.ClientDetails.arguments,
        ) {
            ClientDetailsRoute()
        }
    }
}