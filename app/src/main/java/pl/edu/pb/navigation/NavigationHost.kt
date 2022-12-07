package pl.edu.pb.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import pl.edu.pb.common.navigation.NavigationDestination
import pl.edu.pb.common.navigation.NavigationFactory

@Composable
fun NavigationHost(
    navController: NavHostController,
    factories: Set<NavigationFactory>,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.Login.route,
        modifier = modifier,
    ) {
        factories.forEach {
            it.create(this)
        }
    }
}