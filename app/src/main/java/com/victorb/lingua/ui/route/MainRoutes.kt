package com.victorb.lingua.ui.route

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.victorb.lingua.ui.home.HomeRoute

@Composable
fun MainRoutes() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Home.route) {
        composable(Routes.Home) { HomeRoute(navController) }
    }
}

private fun NavGraphBuilder.composable(
    route: Routes,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) = composable(route.route, arguments, deepLinks, content)