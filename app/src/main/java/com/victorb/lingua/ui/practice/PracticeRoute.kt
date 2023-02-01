package com.victorb.lingua.ui.practice

import android.view.KeyEvent.KEYCODE_ENTER
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import com.victorb.lingua.R
import com.victorb.lingua.ui.designsystem.component.LinguaAppBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private val rightColor = Color(136, 255, 0, 64)
private val wrongColor = Color(255, 0, 0, 64)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PracticeRoute(
    navController: NavController,
    deckId: String,
    viewModel: PracticeViewModel = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(deckId) {
        launch {
            delay(100)
            viewModel.loadPractice(deckId)
        }

        viewModel.action.flowWithLifecycle(lifecycle).collectLatest { action ->
            when (action) {
                PracticeAction.NavigateUp -> navController.navigateUp()
                PracticeAction.CloseKeyboard -> keyboardController?.hide()
                PracticeAction.OpenKeyboard -> {
                    focusRequester.requestFocus()
                    keyboardController?.show()
                }
            }
        }
    }

    PracticeScreen(
        state = viewModel.state,
        onAnswerChanged = viewModel::onAnswerChanged,
        onKeyEvent = viewModel::onKeyEvent,
        onContinue = viewModel::onContinue,
        onNavigateUp = { navController.navigateUp() },
        inputFocusRequester = focusRequester
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun PracticeScreen(
    state: PracticeState,
    onAnswerChanged: (String) -> Unit,
    onKeyEvent: (NativeKeyEvent) -> Boolean,
    onContinue: () -> Unit,
    onNavigateUp: () -> Unit,
    inputFocusRequester: FocusRequester,
    modifier: Modifier = Modifier,
) {
    val initialBackgroundColor = MaterialTheme.colorScheme.surface

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

                    Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                        Text(
                            text = state.question,
                            fontSize = 28.sp,
                            textAlign = TextAlign.Center,
                        )
                    }

                    Spacer(modifier = Modifier.height(130.dp))

                    TextField(
                        value = state.answer,
                        onValueChange = onAnswerChanged,
                        label = { Text(text = "Answer") },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { onContinue() }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .focusRequester(inputFocusRequester)
                            .onKeyEvent { onKeyEvent(it.nativeKeyEvent) }
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    Button(
                        onClick = onContinue,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = state.mainButtonColorRes)
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = stringResource(id = state.mainButtonTextRes)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    AnimatedVisibility(visible = state.infoText != null) {
                        val bgColor = state.infoBackgroundColorRes ?: return@AnimatedVisibility

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(colorResource(id = bgColor))
                                .padding(horizontal = 12.dp, vertical = 20.dp)
                        ) {
                            Text(buildAnnotatedString {
                                withStyle(
                                    SpanStyle(
                                        color = Color.White,
                                        fontSize = 18.sp
                                    )
                                ) {
                                    append(stringResource(id = R.string.correct_answer_info))
                                }

                                append(" ")

                                withStyle(
                                    SpanStyle(
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append(state.infoText ?: "")
                                }
                            }
                            )
                        }
                    }
                }
            }

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
