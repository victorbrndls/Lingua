package com.victorb.lingua.ui.mydeck.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorb.lingua.core.mydeck.entity.MyDeck
import com.victorb.lingua.core.mydeck.usecase.MyDecksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyDeckListViewModel @Inject constructor(
    myDecksUseCase: MyDecksUseCase
) : ViewModel() {

    var decks: StateFlow<List<MyDeckModel>> = myDecksUseCase
        .observeMyDecks()
        .map { decks -> decks.toModel() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun onDeckClicked(model: MyDeckModel) {

    }

    private fun List<MyDeck>.toModel() = map { deck ->
        MyDeckModel(
            id = deck.id,
            title = deck.title,
            progress = "${deck.learnedCards}/${deck.totalCards}"
        )
    }

}