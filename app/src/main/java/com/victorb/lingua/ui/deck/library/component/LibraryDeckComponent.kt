package com.victorb.lingua.ui.deck.library.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryDeckComponent(
    model: LibraryDeckModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = model.title,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
        )
    }
}

data class LibraryDeckModel(
    val id: String,
    val title: String,
)