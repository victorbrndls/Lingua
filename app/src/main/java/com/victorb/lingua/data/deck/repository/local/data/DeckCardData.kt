package com.victorb.lingua.data.deck.repository.local.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "deck_card",
    foreignKeys = [
        ForeignKey(
            DeckData::class,
            parentColumns = ["id"],
            childColumns = ["deck_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DeckCardData(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "deck_id") val deckId: String,
    @ColumnInfo(name = "input") val input: String?
)
