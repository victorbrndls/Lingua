package com.victorb.lingua.ui.mydeck.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MyDeckState {

    var isLoading by mutableStateOf(false)

    var decks: List<MyDeckModel> by mutableStateOf(emptyList())

}