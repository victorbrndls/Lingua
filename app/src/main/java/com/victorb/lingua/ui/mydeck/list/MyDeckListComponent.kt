package com.victorb.lingua.ui.mydeck.list

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import com.victorb.lingua.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyDeckListComponent(
    modifier: Modifier = Modifier,
    viewModel: MyDeckListViewModel = hiltViewModel(),
    onNavigateToPractice: (String) -> Unit,
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val decks by viewModel.decks.collectAsState()

    LaunchedEffect(true) {
        viewModel.action.flowWithLifecycle(lifecycle).collectLatest { action ->
            when (action) {
                is MyDeckListAction.NavigateToPractice ->
                    onNavigateToPractice(action.deckId)
                MyDeckListAction.ShowNoCardsToReviewInfo ->
                    Toast.makeText(context, R.string.no_cards_to_review, Toast.LENGTH_SHORT).show()
            }
        }
    }

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