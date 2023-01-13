package com.victorb.lingua.ui.deck.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorb.lingua.core.card.dto.SaveDeckCardData
import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.card.usecase.GetDeckCardUseCase
import com.victorb.lingua.core.card.usecase.SaveDeckCardUseCase
import com.victorb.lingua.infrastructure.ktx.onFinally
import com.victorb.lingua.infrastructure.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCardViewModel @Inject constructor(
    private val saveCardUseCase: SaveDeckCardUseCase,
    private val getDeckCardUseCase: GetDeckCardUseCase,
) : ViewModel() {

    val state: EditCardState = EditCardState()

    private val _action = MutableSharedFlow<EditCardAction>()
    val action: Flow<EditCardAction> = _action

    fun load(params: EditCardNavigationParams) {
        when (params) {
            is EditCardNavigationParams.AddCard -> createNewCard(params.deckId)
            is EditCardNavigationParams.EditCard -> loadCard(params.cardId)
        }
    }

    private fun createNewCard(deckId: String) {
        state.deckId = deckId
    }

    private fun loadCard(cardId: String) {
        state.id = cardId

        viewModelScope.launch {
            runCatching {
                state.isLoading = true
                val card = getDeckCardUseCase.get(cardId) ?: return@launch
                state.applyEntity(card)
            }
                .onFinally { state.isLoading = false }
        }
        // TODO load card
    }

    fun save() {
        if (!validateState()) return

        val data = SaveDeckCardData(
            state.id.ifBlank { null },
            state.deckId,
            state.input,
            state.outputs
        )

        viewModelScope.launch {
            runCatching {
                state.isLoading = true
                saveCardUseCase.save(data)
            }
                .onFinally { state.isLoading = false }
                .onSuccess { _action.emit(EditCardAction.NavigateBack) }
        }
    }

    private fun validateState(): Boolean {
        if (state.input.isBlank()) return false
        if (state.outputs.all { it.isBlank() }) return false

        if (state.deckId.isBlank()) {
            Logger.e("Deck is missing but that's an invalid state")
            return false
        }

        return true
    }

    private fun EditCardState.applyEntity(card: DeckCard) {
        id = card.id
        deckId = card.deckId
        input = card.input
        outputs = card.outputs
    }

}

sealed interface EditCardAction {
    object NavigateBack : EditCardAction
}