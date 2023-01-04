package com.victorb.lingua.ui.route

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.victorb.lingua.ui.deck.library.DeckLibraryRoute
import com.victorb.lingua.ui.home.HomeRoute

@Composable
fun MainRoutes() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.DeckLibrary.route) {
        composable(Routes.Home) { HomeRoute(navController) }
        composable(Routes.DeckLibrary) { DeckLibraryRoute(navController) }
    }
}

private fun NavGraphBuilder.composable(
    route: Routes,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) = composable(route.route, arguments, deepLinks, content)

fun NavController.navigate(route: Routes) = navigate(route.route)