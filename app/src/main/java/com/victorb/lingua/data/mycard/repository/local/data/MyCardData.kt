package com.victorb.lingua.data.mycard.repository.local.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.victorb.lingua.data.deck.repository.local.data.DeckCardData

@Entity(
    tableName = "my_card",
    foreignKeys = [
        ForeignKey(
            DeckCardData::class,
            parentColumns = ["id"],
            childColumns = ["card_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MyCardData(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "card_id") val cardId: String,
)
