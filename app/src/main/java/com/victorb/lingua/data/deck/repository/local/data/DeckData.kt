package com.victorb.lingua.data.deck.repository.local.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "deck"
)
data class DeckData(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val title: String?
)
