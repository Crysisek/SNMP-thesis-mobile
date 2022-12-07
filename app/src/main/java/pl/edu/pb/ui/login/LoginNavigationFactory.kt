package pl.edu.pb.ui.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.edu.pb.common.navigation.NavigationDestination
import pl.edu.pb.common.navigation.NavigationFactory
import pl.edu.pb.ui.login.composable.LoginRoute
import javax.inject.Inject

class LoginNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder) {
        builder.composable(
            route = NavigationDestination.Login.route,
            arguments = NavigationDestination.Login.arguments,
        ) {
            LoginRoute()
        }
    }
}