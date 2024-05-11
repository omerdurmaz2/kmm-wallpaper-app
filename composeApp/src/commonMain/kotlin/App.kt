import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import di.appModule
import org.koin.compose.KoinApplication
import screens.detail.DetailScreen
import screens.home.HomeScreen

@Composable
fun App(navController: NavHostController = rememberNavController()) {
    KoinApplication(application = {
        modules(appModule())
    }) {

        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = WallpaperScreens.valueOf(
            backStackEntry?.destination?.route ?: WallpaperScreens.Home.name
        )

        MaterialTheme {
            Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                WallpaperAppBar(
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() }
                )
            }) {
                NavHost(
                    navController = navController,
                    modifier = Modifier.fillMaxSize(),
                    startDestination = WallpaperScreens.Home.name,
                    builder = {
                        composable(
                            route = WallpaperScreens.Home.name,
                            content = {
                                HomeScreen(navigateToDetail = {
                                    navController.navigate(WallpaperScreens.Detail.name)
                                })
                            })

                        composable(
                            route = WallpaperScreens.Detail.name,
                            content = {
                                DetailScreen()
                            })
                    }
                )
            }
        }
    }
}

@Composable
fun WallpaperAppBar(
    currentScreen: WallpaperScreens,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(currentScreen.title) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = currentScreen.title
                    )
                }
            }
        }
    )
}

enum class WallpaperScreens(val title: String) {
    Home("home"),
    Detail("detail"),
}