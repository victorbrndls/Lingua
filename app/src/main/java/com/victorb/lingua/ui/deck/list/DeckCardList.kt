package com.victorb.lingua.ui.deck.list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.victorb.lingua.ui.deck.card.DeckCard
import com.victorb.lingua.ui.deck.card.DeckCardModel

@Composable
fun DeckCardListComponent(
    modifier: Modifier = Modifier,
    viewModel: DeckCardListViewModel = hiltViewModel(),
) {
    DeckCardList(
        cards = viewModel.cards,
        modifier = modifier
    )
}

@Composable
fun DeckCardList(
    cards: List<DeckCardModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(cards) {
            DeckCard(
                model = it,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
    }
}