package com.victorb.lingua.core.practice.usecase

import com.victorb.lingua.core.practice.entity.PracticeSession
import com.victorb.lingua.core.practice.repository.PracticeRepository
import javax.inject.Inject

interface GetPracticeSessionUseCase {

    suspend fun getSession(deckId: String): PracticeSession?

}

class GetPracticeSessionUseCaseImpl @Inject constructor(
    private val repository: PracticeRepository
) : GetPracticeSessionUseCase {

    override suspend fun getSession(deckId: String): PracticeSession? {
        return repository.getSession(deckId)
    }

}