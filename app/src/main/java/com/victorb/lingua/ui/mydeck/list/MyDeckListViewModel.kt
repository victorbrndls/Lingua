package com.victorb.lingua.ui.mydeck.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorb.lingua.core.mydeck.entity.MyDeck
import com.victorb.lingua.core.mydeck.usecase.MyDecksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyDeckListViewModel @Inject constructor(
    myDecksUseCase: MyDecksUseCase
) : ViewModel() {

    private val _action = MutableSharedFlow<MyDeckListAction>()
    val action: Flow<MyDeckListAction> = _action

    var decks: StateFlow<List<MyDeckModel>> = myDecksUseCase
        .observeMyDecks()
        .map { decks -> decks.toModel() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun onDeckClicked(model: MyDeckModel) {
        viewModelScope.launch { _action.emit(MyDeckListAction.NavigateToPractice(model.deckId)) }
    }

    private fun List<MyDeck>.toModel() = map { myDeck ->
        MyDeckModel(
            id = myDeck.id,
            deckId = myDeck.deckId,
            title = myDeck.title,
            cardsToReview = myDeck.cardsToReview.toString(),
            totalProgress = "${myDeck.learnedCards}/${myDeck.totalCards}"
        )
    }

}

sealed interface MyDeckListAction {
    data class NavigateToPractice(val deckId: String) : MyDeckListAction
}