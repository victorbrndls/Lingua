package com.victorb.lingua.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.victorb.lingua.ui.mydeck.list.MyDeckListComponent
import com.victorb.lingua.ui.route.Routes
import com.victorb.lingua.ui.route.navigate

@Composable
fun HomeRoute(
    navController: NavController,
) {
    HomeScreen(
        onNavigateToListDeck = { navController.navigate(Routes.DeckLibrary) },
        onNavigateToPractice = { navController.navigate(Routes.Practice.createRoute(it)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToListDeck: () -> Unit,
    onNavigateToPractice: (String) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToListDeck) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Deck")
            }
        }
    ) { innerPadding ->
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

                MyDeckListComponent(
                    onNavigateToPractice = onNavigateToPractice,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
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
