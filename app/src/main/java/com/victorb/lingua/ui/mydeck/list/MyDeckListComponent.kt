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
        state = viewModel.state,
        onDeckClick = viewModel::onDeckClicked,
        modifier = modifier
    )
}

@Composable
fun DeckCardList(
    state: MyDeckState,
    onDeckClick: (MyDeckModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(state.decks) { model ->
            MyDeckComponent(
                model = model,
                onClick = { onDeckClick(model) },
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
    }
}