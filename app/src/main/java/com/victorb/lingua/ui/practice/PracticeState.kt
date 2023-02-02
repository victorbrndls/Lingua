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

    var practiceType: PracticeTypeModel? by mutableStateOf(null)
    var question: String by mutableStateOf("")
    var answer: String by mutableStateOf("")

    val isAnswerFieldEnabled by derivedStateOf { infoText.isNullOrBlank() }

    var progress: Float by mutableStateOf(0f) // 0f..1f

    var mainButtonTextRes by mutableStateOf(R.string.practice_check)
    var mainButtonColorRes by mutableStateOf(R.color.check_unknown_answer)

    var infoText: String? by mutableStateOf(null)
    var infoBackgroundColorRes: Int? by mutableStateOf(null)

    var flickerBackground: TimedObject<Boolean>? by mutableStateOf(null)

}

sealed interface PracticeTypeModel {
    class TypeAnswer(question: String) : PracticeTypeModel {
        var question: String by mutableStateOf(question)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as TypeAnswer

            if (question != other.question) return false

            return true
        }

        override fun hashCode(): Int {
            return question.hashCode()
        }
    }

    class MultipleOptions(question: String, options: List<String>) : PracticeTypeModel {
        var question: String by mutableStateOf(question)
        var options: List<String> by mutableStateOf(options)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as MultipleOptions

            if (options != other.options) return false

            return true
        }

        override fun hashCode(): Int {
            return options.hashCode()
        }
    }
}
