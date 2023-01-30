package com.victorb.lingua.ui.practice

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.victorb.lingua.R
import com.victorb.lingua.infrastructure.TimedObject

class PracticeState {

    var isLoading: Boolean by mutableStateOf(false)

    var title: String by mutableStateOf("")

    var question: String by mutableStateOf("")
    var answer: String by mutableStateOf("")

    // 0f..1f
    var progress: Float by mutableStateOf(0f)

    var continueButtonTextRes by mutableStateOf(R.string.practice_check_button)
    var continueButtonColorRes by mutableStateOf(R.color.continue_unknown_answer)

    var infoText: String? by mutableStateOf(null)
    var infoBackgroundColorRes: Int? by mutableStateOf(null)

    var flickerBackground: TimedObject<Boolean>? by mutableStateOf(null)

}

