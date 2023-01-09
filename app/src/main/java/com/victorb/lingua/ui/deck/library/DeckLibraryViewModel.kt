package com.victorb.lingua.ui.deck.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.victorb.lingua.ui.deck.library.card.LibraryDeckCardModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class DeckLibraryViewModel @Inject constructor() : ViewModel() {

    var cards: List<LibraryDeckCardModel> by mutableStateOf(emptyList())
        private set

    init {
        loadCards()
    }

    private fun loadCards() {
        cards = listOf(
            LibraryDeckCardModel(
                title = "100 Most Popular Italian Words",
            ),
            LibraryDeckCardModel(
                title = "10 Most Popular Portuguese Verbs",
            ),
            LibraryDeckCardModel(
                title = "Useful words when visiting Germany",
            ),
        )
    }

}