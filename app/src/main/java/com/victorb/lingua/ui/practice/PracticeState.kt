package com.victorb.lingua.ui.practice

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.victorb.lingua.R
import com.victorb.lingua.infrastructure.TimedObject

class PracticeState {

    var isLoading: Boolean by mutableStateOf(false)

    var title: String by mutableStateOf("")

    var cardPractice: CardPracticeModel? by mutableStateOf(null)
    var answer: String by mutableStateOf("")

    val isAnswerFieldEnabled by derivedStateOf { infoText.isNullOrBlank() }

    var progress: Float by mutableStateOf(0f) // 0f..1f

    var mainButtonTextRes by mutableStateOf(R.string.practice_check)
    var mainButtonColorRes by mutableStateOf(R.color.check_unknown_answer)

    var infoText: String? by mutableStateOf(null)
    var infoBackgroundColorRes: Int? by mutableStateOf(null)

    var flickerBackground: TimedObject<Boolean>? by mutableStateOf(null)

}