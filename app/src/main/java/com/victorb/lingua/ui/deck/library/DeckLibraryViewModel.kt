package com.victorb.lingua.ui.deck.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorb.lingua.core.deck.entity.Deck
import com.victorb.lingua.core.deck.usecase.GetDecksUseCase
import com.victorb.lingua.infrastructure.ktx.onFinally
import com.victorb.lingua.ui.deck.library.component.LibraryDeckModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeckLibraryViewModel @Inject constructor(
    private val getDecksUseCase: GetDecksUseCase
) : ViewModel() {

    var state = DeckLibraryState()

    private val _action = MutableSharedFlow<DeckLibraryAction>()
    val action: Flow<DeckLibraryAction> = _action

    fun loadCards() {
        viewModelScope.launch { // todo: how to handle when deck changes? (observable or fetch again?)
            runCatching {
                state.isLoading = true
                state.decks = getDecksUseCase.get().toModel()
            }
                .onFinally { state.isLoading = false }
        }
    }

    fun onDeckClicked(deckModel: LibraryDeckModel) {
        viewModelScope.launch { _action.emit(DeckLibraryAction.NavigateToEditDeck(deckModel.id)) }
    }

    private fun List<Deck>.toModel() = map { deck ->
        LibraryDeckModel(
            id = deck.id,
            title = deck.title,
        )
    }

}

sealed interface DeckLibraryAction {
    data class NavigateToEditDeck(val deckId: String) : DeckLibraryAction
}