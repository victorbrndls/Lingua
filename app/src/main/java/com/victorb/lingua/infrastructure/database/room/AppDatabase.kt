package com.victorb.lingua.infrastructure.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.victorb.lingua.data.deck.repository.dao.DeckDao
import com.victorb.lingua.data.deck.repository.data.DeckData

@Database(
    entities = [
        DeckData::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deckDao(): DeckDao
}
