package com.victorb.lingua.ui.deck.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import com.victorb.lingua.ui.designsystem.component.LinguaAppBar
import com.victorb.lingua.ui.route.Routes
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditDeckRoute(
    navController: NavController,
    deckId: String?,
    viewModel: EditDeckViewModel = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(deckId ?: "none") {
        viewModel.loadDeck(deckId)

        viewModel.action.flowWithLifecycle(lifecycle).collectLatest { action ->
            when (action) {
                is EditDeckAction.NavigateToAddCard ->
                    navController.navigate(Routes.AddCard.createRoute(action.deckId))
                is EditDeckAction.NavigateToEditCard ->
                    navController.navigate(Routes.EditCard.createRoute(action.cardId))
                EditDeckAction.NavigateUp ->
                    navController.navigateUp()
            }
        }
    }

    EditDeckScreen(
        state = viewModel.state,
        onNavigateUp = navController::navigateUp,
        onAddNewCard = viewModel::addNewCard,
        onSave = viewModel::save,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun EditDeckScreen(
    modifier: Modifier = Modifier,
    state: EditDeckState,
    onNavigateUp: () -> Unit,
    onAddNewCard: () -> Unit,
    onSave: () -> Unit,
) {
    Scaffold(
        topBar = {
            LinguaAppBar(
                title = if (state.id.isNotBlank()) "Edit Deck" else "Add Deck",
                onNavigateUp = onNavigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onSave) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Save Deck")
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
                    value = state.title,
                    label = { Text("Title") },
                    onValueChange = { state.title = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Drag cards to reorder them",
                    modifier = Modifier.alpha(0.7f)
                )
                Text(
                    text = "Tap cards to edit them",
                    modifier = Modifier.alpha(0.7f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(state.cards) { model ->
                        CardModel(
                            model = model,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onAddNewCard,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "New Card")
                }
            }
        }
    }
}

@Composable
private fun CardModel(
    model: EditDeckCardModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = model.source,
            fontSize = 18.sp,
            modifier = Modifier
        )
        model.translations.forEach { translation ->
            Text(
                text = translation,
                fontSize = 12.sp,
                modifier = Modifier
            )
        }
    }
}