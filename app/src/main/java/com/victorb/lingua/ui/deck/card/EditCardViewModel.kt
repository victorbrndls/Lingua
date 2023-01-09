package com.victorb.lingua.ui.deck.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorb.lingua.infrastructure.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditCardViewModel @Inject constructor() : ViewModel() {

    val state: EditCardState = EditCardState()

    private val _action = MutableSharedFlow<EditCardAction>()
    val action: Flow<EditCardAction> = _action

    fun load(deckId: String?, cardId: String?) {
        when {
            deckId == null && cardId == null -> {
                Logger.e("deckId and cardId are null")
                viewModelScope.launch { _action.emit(EditCardAction.NavigateUp) }
                return
            }
            deckId != null -> createNewCard(deckId)
            cardId != null -> loadCard(cardId)
        }

    }

    private fun createNewCard(deckId: String) {
        state.deckId = deckId
    }

    private fun loadCard(cardId: String) {
        state.id = cardId
        // TODO load card
    }

    fun saveCard() {

    }

}

sealed class EditCardAction {
    object NavigateUp : EditCardAction()
}