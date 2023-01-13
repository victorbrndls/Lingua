package com.victorb.lingua.ui.mydeck.list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MyDeckListComponent(
    modifier: Modifier = Modifier,
    viewModel: MyDeckListViewModel = hiltViewModel(),
) {
    DeckCardList(
        cards = viewModel.cards,
        modifier = modifier
    )
}

@Composable
fun DeckCardList(
    cards: List<MyDeckModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(cards) {
            MyDeckComponent(
                model = it,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
    }
}