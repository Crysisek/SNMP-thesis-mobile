package pl.edu.pb.common.navigation

import kotlinx.coroutines.flow.Flow

interface NavigationManager {
    val navigationEvent: Flow<NavigationCommand>
    fun navigate(command: NavigationCommand)
    fun navigateUp()
}
