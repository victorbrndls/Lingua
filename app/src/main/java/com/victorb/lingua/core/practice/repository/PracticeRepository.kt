package com.victorb.lingua.core.practice.repository

import com.victorb.lingua.core.practice.entity.PracticeSession

interface PracticeRepository {

    suspend fun getSession(deckId: String): PracticeSession?

}