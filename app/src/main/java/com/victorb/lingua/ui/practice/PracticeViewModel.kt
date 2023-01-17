package com.victorb.lingua.ui.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.practice.entity.PracticeSession
import com.victorb.lingua.core.practice.usecase.CheckPracticeAnswerUseCase
import com.victorb.lingua.core.practice.usecase.GetPracticeSessionUseCase
import com.victorb.lingua.infrastructure.ktx.onFinally
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val getPracticeSessionUseCase: GetPracticeSessionUseCase,
    private val checkPracticeAnswerUseCase: CheckPracticeAnswerUseCase
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
                        // todo: handle error
                        return@onSuccess
                    }

                    session = practice
                    cardsLeft = practice.cards
                    state.applyEntity(practice)
                    loadNextQuestion()
                }
        }
    }

    fun checkAnswer() {
        val card = currentCard ?: return

        val isCorrect = checkPracticeAnswerUseCase.checkAnswer(card, state.answer)

        state.progress = 1f - (1f / session.cards.size * cardsLeft.size)
        loadNextQuestion()
    }

    private fun loadNextQuestion() {
        val nextCard = cardsLeft.firstOrNull() ?: run {
            endPractice()
            return
        }

        cardsLeft = cardsLeft - nextCard
        currentCard = nextCard
        state.question = nextCard.input
        state.answer = ""
    }

    private fun endPractice() {
        viewModelScope.launch {
            _action.emit(PracticeAction.NavigateUp)
        }
    }

    private fun PracticeState.applyEntity(practice: PracticeSession) {
        title = practice.title
    }

}

sealed interface PracticeAction {
    object NavigateUp : PracticeAction
}