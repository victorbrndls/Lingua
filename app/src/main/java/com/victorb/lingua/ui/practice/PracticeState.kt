package com.victorb.lingua.ui.practice

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class PracticeState {

    var isLoading: Boolean by mutableStateOf(false)

    var title: String by mutableStateOf("")

    var question: String by mutableStateOf("")
    var answer: String by mutableStateOf("")

    // 0f..1f
    var progress: Float by mutableStateOf(0f)

}