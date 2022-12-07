package pl.edu.pb.ui.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.edu.pb.common.navigation.NavigationDestination
import pl.edu.pb.common.navigation.NavigationFactory
import javax.inject.Inject

class SettingsNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder) {
        builder.composable(
            route = NavigationDestination.Settings.route,
            arguments = NavigationDestination.Settings.arguments,
        ) {
            SettingsRoute()
        }
    }
}