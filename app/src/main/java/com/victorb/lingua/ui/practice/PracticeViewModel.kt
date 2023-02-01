package com.victorb.lingua.ui.practice

import android.view.KeyEvent
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorb.lingua.R
import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.practice.entity.PracticeSession
import com.victorb.lingua.core.practice.usecase.CheckPracticeAnswerResponse
import com.victorb.lingua.core.practice.usecase.CheckPracticeAnswerUseCase
import com.victorb.lingua.core.practice.usecase.GetPracticeSessionUseCase
import com.victorb.lingua.core.practice.usecase.PracticeCardUseCase
import com.victorb.lingua.infrastructure.TimedObject
import com.victorb.lingua.infrastructure.ktx.onFinally
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val KEY_INPUT_DEBOUNCE_DELAY = 50L

@OptIn(FlowPreview::class)
@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val getPracticeSessionUseCase: GetPracticeSessionUseCase,
    private val checkPracticeAnswerUseCase: CheckPracticeAnswerUseCase,
    private val practiceCardUseCase: PracticeCardUseCase,
) : ViewModel() {

    private val _action = MutableSharedFlow<PracticeAction>()
    val action: Flow<PracticeAction> = _action

    private val enterEventChannel = MutableSharedFlow<Any>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val state = PracticeState()

    private lateinit var session: PracticeSession
    private var cardsLeft: List<DeckCard> = emptyList()
    private var currentCard: DeckCard? = null

    init {
        viewModelScope.launch {
            enterEventChannel
                .debounce(KEY_INPUT_DEBOUNCE_DELAY)
                .collect { onContinue() }
        }
    }

    fun loadPractice(deckId: String) {
        viewModelScope.launch {
            runCatching {
                state.isLoading = true
                getPracticeSessionUseCase.getSession(deckId)
            }
                .onFinally { state.isLoading = false }
                .onSuccess { practice ->
                    practice ?: run {
                        viewModelScope.launch { _action.emit(PracticeAction.NavigateUp) }
                        return@onSuccess
                    }

                    session = practice
                    cardsLeft = practice.cards
                    state.applyEntity(practice)
                    loadNextQuestion()
                }
        }
    }

    fun onAnswerChanged(answer: String) {
        if (!state.isAnswerFieldEnabled) return
        state.answer = answer
    }

    fun onKeyEvent(keyEvent: NativeKeyEvent): Boolean {
        if (keyEvent.keyCode != KeyEvent.KEYCODE_ENTER) return false

        enterEventChannel.tryEmit(Unit)
        return true
    }

    fun onContinue() {
        if (state.infoText != null) {
            loadNextQuestion()
        } else {
            checkAnswer()
        }
    }

    private fun checkAnswer() {
        val card = currentCard ?: return
        val result = checkPracticeAnswerUseCase.checkAnswer(card, state.answer)

        // We don't want to block the practice, just fire and forget
        viewModelScope.launch {
            runCatching { practiceCardUseCase.update(card.id, result.isCorrect) }
                .onFailure { /*todo: show non-interruptive error */ }
        }

        updateStateAfterAnswer(result, card)
    }

    private fun updateStateAfterAnswer(
        result: CheckPracticeAnswerResponse, card: DeckCard
    ) {
        state.flickerBackground = TimedObject.ofNow(result.isCorrect)
        state.progress = 1f - (1f / session.cards.size * cardsLeft.size)

        if ((result as? CheckPracticeAnswerResponse.Correct)?.isExactAnswer == true) {
            loadNextQuestion()
        } else {
            viewModelScope.launch { _action.emit(PracticeAction.CloseKeyboard) }

            val backgroundColor =
                if (result.isCorrect) R.color.continue_correct_answer
                else R.color.continue_wrong_answer

            state.mainButtonTextRes = R.string.practice_continue
            state.infoText = card.outputs.first()
            state.mainButtonColorRes = backgroundColor
            state.infoBackgroundColorRes = backgroundColor
        }
    }

    private fun loadNextQuestion() {
        val nextCard = cardsLeft.firstOrNull() ?: run {
            endPractice()
            return
        }

        cardsLeft = cardsLeft - nextCard
        currentCard = nextCard

        state.question = "${nextCard.input} (${nextCard.outputs})"
        state.answer = ""
        state.mainButtonTextRes = R.string.practice_check
        state.mainButtonColorRes = R.color.check_unknown_answer
        state.infoText = null

        viewModelScope.launch {
            /** The answer text field is disabled if the user gets the answer wrong, it's enabled
             *  again when we set [infoText] to null but that takes a few milliseconds. We can't
             *  open the keyboard until it's enabled so we have a small delay here */
            delay(200)
            _action.emit(PracticeAction.OpenKeyboard)
        }
    }

    private fun endPractice() {
        viewModelScope.launch { _action.emit(PracticeAction.NavigateUp) }
    }

    private fun PracticeState.applyEntity(practice: PracticeSession) {
        title = practice.title
    }

}

sealed interface PracticeAction {
    object NavigateUp : PracticeAction
    object CloseKeyboard : PracticeAction
    object OpenKeyboard : PracticeAction
}