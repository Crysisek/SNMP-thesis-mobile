package pl.edu.pb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.edu.pb.common.extensions.collectWithLifecycle
import pl.edu.pb.common.navigation.NavigationDestination
import pl.edu.pb.common.navigation.NavigationFactory
import pl.edu.pb.common.navigation.NavigationManager
import pl.edu.pb.navigation.bottomnavigation.MainBottomAppBar
import pl.edu.pb.common.ui.MainTheme
import pl.edu.pb.navigation.NavigationHost
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationFactories: @JvmSuppressWildcards Set<NavigationFactory>

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
                val navController = rememberNavController()

                Scaffold(
                    topBar = { MainTopAppBar(navController) },
                    bottomBar = { MainBottomAppBar(navController) },
                ) { paddingValues ->
                    NavigationHost(
                        navController = navController,
                        factories = navigationFactories,
                        Modifier.padding(paddingValues),
                    )
                }

                navigationManager
                    .navigationEvent
                    .collectWithLifecycle(
                        key = navController,
                    ) {
                        if (it.destination.isNotEmpty()) {
                            navController.navigate(it.destination, it.configuration)
                        } else {
                            navController.navigateUp()
                        }
                    }
            }
        }
    }
}

@Composable
private fun MainTopAppBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val appBarText = if (currentRoute == NavigationDestination.ClientDetails.route) {
        navController.currentBackStackEntry?.arguments?.getString(NavigationDestination.ClientDetails.CLIENT_ID)!!
    } else {
        stringResource(id = R.string.app_name)
    }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = appBarText,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        )
    )
}
