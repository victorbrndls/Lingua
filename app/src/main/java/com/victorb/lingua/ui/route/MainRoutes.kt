package com.victorb.lingua.ui.route

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.victorb.lingua.ui.deck.card.EditCardNavigationParams
import com.victorb.lingua.ui.deck.card.EditCardRoute
import com.victorb.lingua.ui.deck.edit.EditDeckRoute
import com.victorb.lingua.ui.deck.library.DeckLibraryRoute
import com.victorb.lingua.ui.home.HomeRoute
import com.victorb.lingua.ui.practice.PracticeRoute

@Composable
fun MainRoutes() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Home.route) {
        composable(Routes.Home) { HomeRoute(navController) }

        composable(Routes.Practice) {
            val deckId = requireNotNull(Routes.Practice.parse(it.arguments)) { "deckId is missing" }
            PracticeRoute(navController, deckId)
        }

        composable(Routes.DeckLibrary) { DeckLibraryRoute(navController) }

        composable(Routes.EditDeck) {
            val deckId = Routes.EditDeck.parse(it.arguments)
            EditDeckRoute(navController = navController, deckId = deckId)
        }

        composable(Routes.AddCard) {
            val deckId = requireNotNull(Routes.AddCard.parse(it.arguments)) { "deckId is missing" }
            EditCardRoute(navController, params = EditCardNavigationParams.AddCard(deckId))
        }

        composable(Routes.EditCard) {
            val cardId = requireNotNull(Routes.EditCard.parse(it.arguments)) { "cardId is missing" }
            EditCardRoute(navController, params = EditCardNavigationParams.EditCard(cardId))
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