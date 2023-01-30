package com.victorb.lingua.ui.practice

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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val getPracticeSessionUseCase: GetPracticeSessionUseCase,
    private val checkPracticeAnswerUseCase: CheckPracticeAnswerUseCase,
    private val practiceCardUseCase: PracticeCardUseCase,
) : ViewModel() {

    private val _action = MutableSharedFlow<PracticeAction>()
    val action: Flow<PracticeAction> = _action

    val state = PracticeState()

    private lateinit var session: PracticeSession
    private var cardsLeft: List<DeckCard> = emptyList()
    private var currentCard: DeckCard? = null

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

        state.flickerBackground = TimedObject.ofNow(result.isCorrect)
        state.progress = 1f - (1f / session.cards.size * cardsLeft.size)

        if ((result as? CheckPracticeAnswerResponse.Correct)?.isExactAnswer == true) {
            loadNextQuestion()
        } else {
            viewModelScope.launch { _action.emit(PracticeAction.CloseKeyboard) }

            state.continueButtonTextRes = R.string.practice_continue_button

            if (result.isCorrect) {
                state.continueButtonColorRes = R.color.continue_correct_answer
                state.infoText = card.outputs.first()
                state.infoBackgroundColorRes = R.color.continue_correct_answer
            } else {
                state.continueButtonColorRes = R.color.continue_wrong_answer
                state.infoText = card.outputs.first()
                state.infoBackgroundColorRes = R.color.continue_wrong_answer
            }
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
        state.continueButtonTextRes = R.string.practice_check_button
        state.continueButtonColorRes = R.color.continue_unknown_answer
        state.infoText = null

        viewModelScope.launch { _action.emit(PracticeAction.OpenKeyboard) }
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