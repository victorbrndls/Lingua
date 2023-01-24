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
        .map { decks -> decks.sortedByDescending { it.cardsToReview } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun onDeckClicked(model: MyDeckModel) {
        viewModelScope.launch {
            val action =
                if (model.cardsToReview <= 0) MyDeckListAction.ShowNoCardsToReviewInfo
                else MyDeckListAction.NavigateToPractice(model.deckId)

            _action.emit(action)
        }
    }

    private fun List<MyDeck>.toModel() = map { myDeck ->
        MyDeckModel(
            id = myDeck.id,
            deckId = myDeck.deckId,
            title = myDeck.title,
            cardsToReview = myDeck.cardsToReview,
            cardsToReviewText = myDeck.cardsToReview.toString(),
            totalProgressText = "${myDeck.learnedCards}/${myDeck.totalCards}"
        )
    }

}

sealed interface MyDeckListAction {
    data class NavigateToPractice(val deckId: String) : MyDeckListAction
    object ShowNoCardsToReviewInfo : MyDeckListAction
}