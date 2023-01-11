package com.victorb.lingua.ui.deck.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorb.lingua.core.card.dto.SaveDeckCardData
import com.victorb.lingua.core.card.usecase.SaveDeckCardUseCase
import com.victorb.lingua.infrastructure.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCardViewModel @Inject constructor(
    private val saveCardUseCase: SaveDeckCardUseCase
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
            saveCardUseCase.save(data)
            //_action.emit()
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

}

sealed class EditCardAction {

}