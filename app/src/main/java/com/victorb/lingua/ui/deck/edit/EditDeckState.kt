package com.victorb.lingua.ui.deck.edit

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Stable
class EditDeckState {

    var isLoading by mutableStateOf(false)
    var isNewDeck by mutableStateOf(true)

    var id by mutableStateOf("")

    var title by mutableStateOf("")

    var cards by mutableStateOf(emptyList<EditDeckCardModel>())

}

@Stable
data class EditDeckCardModel(
    val id: String,
    val source: String,
    val translations: List<String>,
)
