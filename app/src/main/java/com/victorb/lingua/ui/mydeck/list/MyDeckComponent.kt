package com.victorb.lingua.ui.mydeck.list

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.victorb.lingua.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDeckComponent(
    model: MyDeckModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, end = 8.dp)
            ) {
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_schedule_black_24),
                        contentDescription = "Words to review",
                        modifier = Modifier
                            .height(20.dp)
                            .padding(end = 2.dp)
                    )
                    Text(
                        text = model.cardsToReview,
                        modifier = Modifier
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_task_black_24),
                        contentDescription = "Words to learn",
                        modifier = Modifier
                            .height(20.dp)
                            .padding(end = 4.dp)
                    )
                    Text(
                        text = model.totalProgress,
                        modifier = Modifier
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = model.title,
                modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
            )
        }
    }
}

data class MyDeckModel(
    val id: String,
    val deckId: String,
    val title: String,
    val cardsToReview: String,
    val totalProgress: String,
)