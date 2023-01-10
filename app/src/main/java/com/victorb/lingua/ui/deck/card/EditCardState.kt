package com.victorb.lingua.ui.deck.card

import androidx.compose.runtime.*

@Stable
class EditCardState {

    var id by mutableStateOf("")
    var deckId by mutableStateOf("")

    var input by mutableStateOf("hello")

    var outputs by mutableStateOf(emptyList<String>())

    val isSaveEnabled by derivedStateOf {
        input.isNotBlank() && outputs.any { it.isNotBlank() }
    }

}

data class EditDeckCardModel(
    val source: String,
    val translations: List<String>,
)
