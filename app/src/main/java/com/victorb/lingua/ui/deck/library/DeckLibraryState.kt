package com.victorb.lingua.ui.deck.library

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.victorb.lingua.ui.deck.library.component.LibraryDeckModel

@Stable
class DeckLibraryState {

    var isLoading by mutableStateOf(false)

    var decks by mutableStateOf(emptyList<LibraryDeckModel>())

}