package com.victorb.lingua.infrastructure.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.victorb.lingua.data.deck.repository.local.dao.DeckCardDao
import com.victorb.lingua.data.deck.repository.local.dao.DeckDao
import com.victorb.lingua.data.deck.repository.local.data.DeckCardData
import com.victorb.lingua.data.deck.repository.local.data.DeckData

@Database(
    entities = [
        DeckData::class,
        DeckCardData::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deckDao(): DeckDao
    abstract fun deckCardDao(): DeckCardDao
}
