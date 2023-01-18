package com.victorb.lingua.core.practice.usecase

import com.victorb.lingua.core.practice.repository.PracticeRepository
import javax.inject.Inject

interface PracticeCardUseCase {

    suspend fun update(cardId: String, isCorrect: Boolean)

}

class PracticeCardUseCaseImpl @Inject constructor(
    private val repository: PracticeRepository
) : PracticeCardUseCase {

    override suspend fun update(cardId: String, isCorrect: Boolean) {
        repository.update(cardId, isCorrect)
    }

}