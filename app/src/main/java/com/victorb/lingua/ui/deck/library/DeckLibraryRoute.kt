package com.victorb.lingua.ui.deck.library

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.victorb.lingua.ui.deck.list.DeckCardListComponent
import com.victorb.lingua.ui.designsystem.component.LinguaAppBar

@Composable
fun DeckLibraryRoute(
    navController: NavController,
) {
    DeckLibraryScreen(
        onNavigateUp = { navController.navigateUp() }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun DeckLibraryScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            LinguaAppBar(
                title = "Deck Library",
                onNavigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = modifier
                .padding(innerPadding)
                .consumedWindowInsets(innerPadding)
        ) {
            Column {
                DeckCardListComponent(modifier = Modifier.padding(horizontal = 8.dp))
            }
        }
    }
}