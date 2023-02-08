package com.victorb.lingua.di

import android.content.Context
import androidx.room.Room
import com.victorb.lingua.infrastructure.database.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RoomModule {

    companion object {
        @Provides
        @Singleton
        fun database(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = AppDatabase::class.java,
                name = "Lingua"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}