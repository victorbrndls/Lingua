package com.victorb.lingua.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.victorb.lingua.ui.deck.list.DeckCardListComponent

@Composable
fun HomeRoute(
    navController: NavController,
) {
    HomeScreen()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Scaffold { innerPadding ->
        BoxWithConstraints(
            modifier = modifier
                .padding(innerPadding)
                .consumedWindowInsets(innerPadding)
        ) {
            Column {
                Spacer(modifier = Modifier.height(16.dp))

                ProgressCard()

                Spacer(modifier = Modifier.height(16.dp))

                PracticeButton()

                Spacer(modifier = Modifier.height(16.dp))

                DeckCardListComponent(modifier = Modifier.padding(horizontal = 8.dp))
            }
        }
    }
}

@Composable
private fun ProgressCard() {
    Card(
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column {
                Text("14")
                Text("Today")
            }
            Column {
                Text("40")
                Text("This week")
            }
            Column {
                Text("918")
                Text("This month")
            }
        }
    }
}

@Composable
private fun PracticeButton() {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        onClick = { /*TODO*/ }) {
        Text("Practice")
    }
}
