package com.victorb.lingua.ui.practice

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import com.victorb.lingua.ui.designsystem.component.LinguaAppBar
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PracticeRoute(
    navController: NavController,
    deckId: String,
    viewModel: PracticeViewModel = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(deckId) {
        viewModel.loadPractice(deckId)

        viewModel.action.flowWithLifecycle(lifecycle).collectLatest { action ->
            when (action) {
                PracticeAction.NavigateUp -> navController.navigateUp()
            }
        }
    }

    PracticeScreen(
        state = viewModel.state,
        onCheckAnswer = viewModel::checkAnswer,
        onNavigateUp = { navController.navigateUp() }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun PracticeScreen(
    state: PracticeState,
    onCheckAnswer: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            LinguaAppBar(title = state.title, onNavigateUp)
        }
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = modifier
                .padding(innerPadding)
                .consumedWindowInsets(innerPadding)
        ) {
            val progress by animateFloatAsState(targetValue = state.progress)

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(96.dp))

                Text(
                    text = state.question,
                    fontSize = 28.sp,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(130.dp))

                TextField(
                    value = state.answer,
                    onValueChange = { state.answer = it },
                    label = { Text(text = "Answer") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { onCheckAnswer() }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
