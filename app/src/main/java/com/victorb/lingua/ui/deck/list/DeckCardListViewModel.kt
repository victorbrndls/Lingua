package com.victorb.lingua.ui.deck.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeckCardListViewModel @Inject constructor() : ViewModel() {

    var cards: List<DeckCardModel> by mutableStateOf(emptyList())
        private set

    init {
        loadCards()
    }

    private fun loadCards() {
        cards = listOf(
            DeckCardModel(
                title = "100 Most Popular Italian Words",
                progress = "0/100",
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/0/03/Flag_of_Italy.svg/320px-Flag_of_Italy.svg.png",
            ),
            DeckCardModel(
                title = "10 Most Popular Portuguese Verbs",
                progress = "4/10",
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/0/05/Flag_of_Brazil.svg/320px-Flag_of_Brazil.svg.png",
            ),
            DeckCardModel(
                title = "Useful words when visiting Germany",
                progress = "7/34",
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/b/ba/Flag_of_Germany.svg/320px-Flag_of_Germany.svg.png",
            ),
        )
    }

}