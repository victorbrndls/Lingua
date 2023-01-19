package com.victorb.lingua.ui.practice

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import com.victorb.lingua.ui.designsystem.component.LinguaAppBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

private val rightColor = Color(136, 255, 0, 64)
private val wrongColor = Color(255, 0, 0, 64)

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
    val initialBackgroundColor = MaterialTheme.colorScheme.surface
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(true) {
        delay(200)
        focusRequester.requestFocus()
    }

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
            val backgroundColor = remember { Animatable(initialBackgroundColor) }

            LaunchedEffect(state.flickerBackground) {
                val isCorrect = state.flickerBackground?.obj ?: return@LaunchedEffect
                val color = if (isCorrect) rightColor else wrongColor

                backgroundColor.animateTo(color, animationSpec = tween(200))
                backgroundColor.animateTo(initialBackgroundColor, animationSpec = tween(200))
            }

            Surface(
                color = backgroundColor.value,
                modifier = Modifier.fillMaxSize()
            ) {
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
                            .focusRequester(focusRequester)
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
