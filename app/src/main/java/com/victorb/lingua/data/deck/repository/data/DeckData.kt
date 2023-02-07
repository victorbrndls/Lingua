package com.victorb.lingua.data.deck.repository.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("deck")
data class DeckData(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String?
)
