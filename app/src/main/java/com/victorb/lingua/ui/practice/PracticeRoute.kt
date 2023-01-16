package com.victorb.lingua.ui.practice

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun PracticeRoute(
    navController: NavController,
    deckId: String,
    viewModel: PracticeViewModel = hiltViewModel()
) {
    LaunchedEffect(deckId) {
        viewModel.loadDeck(deckId)
    }

    PracticeScreen()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun PracticeScreen(
    modifier: Modifier = Modifier,
) {
    Scaffold() { innerPadding ->
        BoxWithConstraints(
            modifier = modifier
                .padding(innerPadding)
                .consumedWindowInsets(innerPadding)
        ) {
            Column {
                Spacer(modifier = Modifier.height(16.dp))



                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }
}
