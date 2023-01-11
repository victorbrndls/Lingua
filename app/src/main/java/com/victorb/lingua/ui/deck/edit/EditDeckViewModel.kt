package com.victorb.lingua.ui.deck.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorb.lingua.core.card.usecase.ObserverDeckCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditDeckViewModel @Inject constructor(
    private val observerDeckCardsUseCase: ObserverDeckCardsUseCase
) : ViewModel() {

    val state: EditDeckState = EditDeckState()

    private val _action = MutableSharedFlow<EditDeckAction>()
    val action: Flow<EditDeckAction> = _action

    fun loadDeck(deckId: String?) {
        if (deckId != null) {
            state.id = deckId
            state.isNewDeck = false
            state.title = "title_${deckId}"
            state.cards = listOf(
                EditDeckCardModel(
                    source = "Lorem", translations = listOf("Ipsom", "Ipsum")
                ),
                EditDeckCardModel(
                    source = "Lorem", translations = listOf("Ipsom", "Ipsum")
                )
            )
        } else {
            state.id = UUID.randomUUID().toString()
        }

        viewModelScope.launch {
            observerDeckCardsUseCase.observe(state.id).collect { cards ->
                state.cards = cards.map { card ->
                    EditDeckCardModel(card.input, card.outputs)
                }
            }
        }
    }

    fun addNewCard() {
        viewModelScope.launch {
            _action.emit(EditDeckAction.NavigateToAddCard(state.id))
        }
    }

    fun save() {
        viewModelScope.launch {
            _action.emit(EditDeckAction.NavigateUp)
        }
    }

}

sealed interface EditDeckAction {
    data class NavigateToAddCard(val deckId: String) : EditDeckAction
    data class NavigateToEditCard(val cardId: String) : EditDeckAction
    object NavigateUp : EditDeckAction
}