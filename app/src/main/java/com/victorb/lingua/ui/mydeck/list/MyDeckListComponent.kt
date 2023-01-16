package com.victorb.lingua.ui.mydeck.list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MyDeckListComponent(
    modifier: Modifier = Modifier,
    viewModel: MyDeckListViewModel = hiltViewModel(),
) {
    val decks by viewModel.decks.collectAsState()

    DeckCardList(
        decks = decks,
        onDeckClick = viewModel::onDeckClicked,
        modifier = modifier
    )
}

@Composable
fun DeckCardList(
    decks: List<MyDeckModel>,
    onDeckClick: (MyDeckModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(decks) { model ->
            MyDeckComponent(
                model = model,
                onClick = { onDeckClick(model) },
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
    }
}