package com.victorb.lingua.ui.mydeck.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorb.lingua.core.mydeck.entity.MyDeck
import com.victorb.lingua.core.mydeck.usecase.MyDecksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyDeckListViewModel @Inject constructor(
    private val myDecksUseCase: MyDecksUseCase
) : ViewModel() {

    val state = MyDeckState()

    init {
        loadCards()
    }

    private fun loadCards() {
        viewModelScope.launch {
            state.isLoading = true

            myDecksUseCase.observeMyDecks().collectLatest { decks ->
                state.isLoading = false
                state.applyEntity(decks)
            }
        }
    }

    fun onDeckClicked(model: MyDeckModel) {

    }

    private fun MyDeckState.applyEntity(decks: List<MyDeck>) {
        this.decks = decks.toModel()
    }

    private fun List<MyDeck>.toModel() = map { deck ->
        MyDeckModel(deck.id, deck.title, "")
    }

}