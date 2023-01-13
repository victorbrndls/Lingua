package com.victorb.lingua.ui.deck.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorb.lingua.core.deck.entity.Deck
import com.victorb.lingua.core.deck.usecase.GetDecksUseCase
import com.victorb.lingua.infrastructure.ktx.onFinally
import com.victorb.lingua.ui.deck.library.component.LibraryDeckModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeckLibraryViewModel @Inject constructor(
    private val getDecksUseCase: GetDecksUseCase
) : ViewModel() {

    var state = DeckLibraryState()

    fun loadCards() {
        viewModelScope.launch { // todo: how to handle when deck changes? (observable or fetch again?)
            runCatching {
                state.isLoading = true
                state.decks = getDecksUseCase.get().toModel()
            }
                .onFinally { state.isLoading = false }
        }
    }

    private fun List<Deck>.toModel() = map { deck ->
        LibraryDeckModel(
            id = deck.id,
            title = deck.title,
        )
    }

}