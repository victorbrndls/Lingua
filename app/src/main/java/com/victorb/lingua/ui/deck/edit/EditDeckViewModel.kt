package com.victorb.lingua.ui.deck.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.card.usecase.ObserverDeckCardsUseCase
import com.victorb.lingua.core.deck.dto.SaveDeckCardData
import com.victorb.lingua.core.deck.dto.SaveDeckData
import com.victorb.lingua.core.deck.usecase.GetDeckUseCase
import com.victorb.lingua.core.deck.usecase.SaveDeckUseCase
import com.victorb.lingua.infrastructure.ktx.onFinally
import com.victorb.lingua.infrastructure.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditDeckViewModel @Inject constructor(
    private val getDeckUseCase: GetDeckUseCase,
    private val saveDeckUseCase: SaveDeckUseCase,
    private val observerDeckCardsUseCase: ObserverDeckCardsUseCase,
) : ViewModel() {

    val state: EditDeckState = EditDeckState()

    private val _action = MutableSharedFlow<EditDeckAction>()
    val action: Flow<EditDeckAction> = _action

    fun loadDeck(deckId: String?) {
        if (deckId != null) {
            state.id = deckId
            state.isNewDeck = false

            viewModelScope.launch {
                val deck = getDeckUseCase.get(deckId) ?: run {
                    Logger.e("Tried to load a deck that doesn't exist | id: $deckId")
                    return@launch
                }

                state.title = deck.title
                state.cards = deck.cards.toModel()
            }
        } else {
            state.id = UUID.randomUUID().toString()
        }

        viewModelScope.launch {
            observerDeckCardsUseCase.observe(state.id).collect { cards ->
                state.cards = cards.toModel()
            }
        }
    }

    fun addNewCard() {
        viewModelScope.launch {
            _action.emit(EditDeckAction.NavigateToAddCard(state.id))
        }
    }

    fun save() {
        if (!validateState()) return

        val data = SaveDeckData(
            deckId = state.id,
            state.title,
            state.cards.mapIndexed { idx, model -> SaveDeckCardData(model.id, idx) }
        )

        viewModelScope.launch {
            runCatching {
                state.isLoading = true
                saveDeckUseCase.save(data)
            }
                .onFinally { state.isLoading = false }
        }
    }

    private fun validateState(): Boolean {
        if (state.title.isBlank()) return false
        return true
    }

    private fun List<DeckCard>.toModel() = map { card ->
        EditDeckCardModel(card.id, card.input, card.outputs)
    }

}

sealed interface EditDeckAction {
    data class NavigateToAddCard(val deckId: String) : EditDeckAction
    data class NavigateToEditCard(val cardId: String) : EditDeckAction
    object NavigateUp : EditDeckAction
}