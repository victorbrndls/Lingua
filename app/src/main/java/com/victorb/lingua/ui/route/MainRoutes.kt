package com.victorb.lingua.ui.route

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.victorb.lingua.ui.deck.card.EditCardRoute
import com.victorb.lingua.ui.deck.edit.EditDeckRoute
import com.victorb.lingua.ui.deck.library.DeckLibraryRoute
import com.victorb.lingua.ui.home.HomeRoute

@Composable
fun MainRoutes() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.EditDeck.route) {
        composable(Routes.Home) { HomeRoute(navController) }

        composable(Routes.DeckLibrary) { DeckLibraryRoute(navController) }

        composable(Routes.EditDeck) {
            val deckId = Routes.EditDeck.parse(it.arguments)
            EditDeckRoute(navController = navController, deckId = deckId ?: "1234")
        }

        composable(Routes.AddCard) {
            val deckId = Routes.AddCard.parse(it.arguments)
            EditCardRoute(navController, deckId = deckId)
        }

        composable(Routes.EditCard) {
            val cardId = Routes.EditCard.parse(it.arguments)
            EditCardRoute(navController, cardId = cardId)
        }
    }
}

private fun NavGraphBuilder.composable(
    route: Routes,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) = composable(route.route, arguments, deepLinks, content)

fun NavController.navigate(route: Routes) = navigate(route.route)