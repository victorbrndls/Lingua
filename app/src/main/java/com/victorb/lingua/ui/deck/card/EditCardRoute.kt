package com.victorb.lingua.ui.deck.card

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import com.victorb.lingua.ui.designsystem.component.LinguaAppBar
import kotlinx.coroutines.flow.collectLatest

sealed interface EditCardNavigationParams {
    data class AddCard(val deckId: String) : EditCardNavigationParams
    data class EditCard(val cardId: String) : EditCardNavigationParams
}

@Composable
fun EditCardRoute(
    navController: NavController,
    params: EditCardNavigationParams,
    viewModel: EditCardViewModel = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(params) {
        viewModel.load(params)

        viewModel.action.flowWithLifecycle(lifecycle).collectLatest { action ->
            when (action) {
                EditCardAction.NavigateBack -> navController.navigateUp()
            }
        }
    }

    EditCardScreen(
        state = viewModel.state,
        onNavigateUp = { navController.navigateUp() },
        onSave = { viewModel.save() },
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
            val alpha = remember {
                derivedStateOf { if (state.isSaveEnabled) 1f else 0f }
            }

            FloatingActionButton(
                onClick = onSave,
                modifier = Modifier.alpha(alpha.value)
            ) {
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
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Outputs")

                Spacer(modifier = Modifier.height(4.dp))

                TextField(
                    value = state.outputs.getOrNull(0) ?: "",
                    label = { Text("Output") },
                    onValueChange = { state.outputs = listOf(it) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}