package pl.edu.pb.navigation.bottomnavigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import pl.edu.pb.common.navigation.NavigationDestination

@Composable
fun MainBottomAppBar(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val bottomBarVisibility = currentRoute in NavigationDestination.bottomNavigationEntries

    AnimatedVisibility(
        visible = bottomBarVisibility,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        NavigationBar(
            modifier = Modifier
                .clip(ShapeDefaults.Large)
                .navigationBarsPadding(),
            ) {
            NavigationDestination.bottomNavigationEntries.forEach { bottomEntry ->
                NavigationBarItem(
                    selected = currentRoute == bottomEntry,
                    alwaysShowLabel = false,
                    onClick = {
                        navController.navigate(bottomEntry) {
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    },
                    label = {
                        Text(
                            modifier = Modifier.wrapContentSize(unbounded = true),
                            softWrap = false,
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            text = bottomEntry,
                        )
                    },
                    icon = {
                        bottomNavigationIcons[bottomEntry]?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = null,
                            )
                        }
                    },
                )
            }
        }
    }
}

private val bottomNavigationIcons = NavigationDestination.bottomNavigationEntries.zip(
    listOf(
        Icons.Default.Home,
        Icons.Default.Search,
        Icons.Default.Favorite,
        Icons.Default.Settings,
    )
).toMap()
