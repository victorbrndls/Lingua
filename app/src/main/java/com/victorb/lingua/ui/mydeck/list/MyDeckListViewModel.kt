package com.victorb.lingua.ui.mydeck.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyDeckListViewModel @Inject constructor(

) : ViewModel() {

    var cards: List<MyDeckModel> by mutableStateOf(emptyList())
        private set

    init {
        loadCards()
    }

    private fun loadCards() {
        cards = listOf(
            MyDeckModel(
                title = "100 Most Popular Italian Words",
                progress = "0/100",
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/0/03/Flag_of_Italy.svg/320px-Flag_of_Italy.svg.png",
            ),
            MyDeckModel(
                title = "10 Most Popular Portuguese Verbs",
                progress = "4/10",
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/0/05/Flag_of_Brazil.svg/320px-Flag_of_Brazil.svg.png",
            ),
            MyDeckModel(
                title = "Useful words when visiting Germany",
                progress = "7/34",
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/b/ba/Flag_of_Germany.svg/320px-Flag_of_Germany.svg.png",
            ),
        )
    }

}