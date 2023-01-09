package com.victorb.lingua.ui.deck.card

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.victorb.lingua.ui.designsystem.component.LinguaAppBar
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditCardRoute(
    navController: NavController,
    deckId: String? = null,
    cardId: String? = null,
    viewModel: EditCardViewModel = hiltViewModel()
) {
    LaunchedEffect(deckId, cardId) {
        viewModel.load(deckId, cardId)

        viewModel.action.collectLatest { action ->
            when (action) {
                EditCardAction.NavigateUp -> navController.navigateUp()
            }
        }
    }

    EditCardScreen(
        state = viewModel.state,
        onNavigateUp = { navController.navigateUp() },
        onSave = { },
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun EditCardScreen(
    modifier: Modifier = Modifier,
    state: EditCardState,
    onNavigateUp: () -> Unit,
    onSave: () -> Unit,
) {
    Scaffold(
        topBar = {
            LinguaAppBar(
                title = "",
                onNavigateUp = onNavigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onSave) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Save Card")
            }
        },
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = modifier
                .padding(innerPadding)
                .consumedWindowInsets(innerPadding)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = state.input,
                    label = { Text("Input") },
                    onValueChange = { state.input = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Outputs")

                Spacer(modifier = Modifier.height(4.dp))

                TextField(
                    value = state.outputs.getOrNull(0) ?: "",
                    label = { Text("Output") },
                    onValueChange = { state.outputs = listOf(it) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}