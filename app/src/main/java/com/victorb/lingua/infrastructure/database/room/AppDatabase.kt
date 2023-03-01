package com.victorb.lingua.infrastructure.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.victorb.lingua.data.deck.repository.local.dao.DeckCardDao
import com.victorb.lingua.data.deck.repository.local.dao.DeckCardOutputDao
import com.victorb.lingua.data.deck.repository.local.dao.DeckDao
import com.victorb.lingua.data.deck.repository.local.data.DeckCardData
import com.victorb.lingua.data.deck.repository.local.data.DeckCardOutputData
import com.victorb.lingua.data.deck.repository.local.data.DeckData
import com.victorb.lingua.data.mycard.repository.local.dao.MyCardDao
import com.victorb.lingua.data.mycard.repository.local.data.MyCardData

@Database(
    entities = [
        DeckData::class,
        DeckCardData::class,
        DeckCardOutputData::class,
        MyCardData::class,
    ], version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deckDao(): DeckDao
    abstract fun deckCardDao(): DeckCardDao
    abstract fun deckCardOutputDao(): DeckCardOutputDao
    abstract fun myCard(): MyCardDao
}
