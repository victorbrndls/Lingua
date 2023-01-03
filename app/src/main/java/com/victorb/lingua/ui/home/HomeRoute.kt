package com.victorb.lingua.ui.home

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

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
            Text("hello")
        }
    }
}