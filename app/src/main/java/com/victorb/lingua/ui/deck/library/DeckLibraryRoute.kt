package com.victorb.lingua.ui.deck.library

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.victorb.lingua.ui.deck.library.component.LibraryDeckComponent
import com.victorb.lingua.ui.designsystem.component.LinguaAppBar
import com.victorb.lingua.ui.route.Routes

@Composable
fun DeckLibraryRoute(
    navController: NavController,
    viewModel: DeckLibraryViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = null) {
        viewModel.loadCards()
    }

    DeckLibraryScreen(
        state = viewModel.state,
        onNavigateUp = { navController.navigateUp() },
        onNavigateToAddDeck = { navController.navigate(Routes.EditDeck.createRoute(null)) },
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun DeckLibraryScreen(
    modifier: Modifier = Modifier,
    state: DeckLibraryState,
    onNavigateUp: () -> Unit,
    onNavigateToAddDeck: () -> Unit,
) {
    Scaffold(
        topBar = {
            LinguaAppBar(
                title = "Deck Library",
                onNavigateUp = onNavigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddDeck) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Deck")
            }
        },
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = modifier
                .padding(innerPadding)
                .consumedWindowInsets(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                items(state.decks, key = { it.id }) {
                    LibraryDeckComponent(
                        model = it,
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                }
            }

        }
    }
}