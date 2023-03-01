package com.victorb.lingua.data.deck.repository.local.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "deck_card_output",
    foreignKeys = [
        ForeignKey(
            DeckCardData::class,
            parentColumns = ["id"],
            childColumns = ["deck_card_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DeckCardOutputData(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "deck_id") val deckId: String,
    @ColumnInfo(name = "deck_card_id") val cardId: String,
    @ColumnInfo(name = "output") val output: String,
    // TODO: support position so order doesn't change
)
