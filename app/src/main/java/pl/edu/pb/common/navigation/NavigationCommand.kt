package pl.edu.pb.common.navigation

import androidx.navigation.NavOptions

interface NavigationCommand {
    val destination: String
    val configuration: NavOptions
        get() = NavOptions.Builder().build()
}
