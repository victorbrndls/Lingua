package com.victorb.lingua.ui.mydeck.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun MyDeckComponent(
    model: MyDeckModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(bottomEnd = 8.dp),
                ) {
                    AsyncImage(
                        model = model.imageUrl,
                        contentDescription = "Icon",
                        // TODO: add placeholder
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .width(36.dp)
                            .aspectRatio(16 / 9f)
                    )
                }

                Text(
                    text = model.progress,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = model.title,
                    modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                )
            }
        }
    }
}

data class MyDeckModel(
    val title: String,
    val progress: String,
    val imageUrl: String,
)