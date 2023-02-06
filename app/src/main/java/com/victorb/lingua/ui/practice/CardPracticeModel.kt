package com.victorb.lingua.ui.practice

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

sealed interface CardPracticeModel {

    class TextField(question: String) : CardPracticeModel {
        var question: String by mutableStateOf(question)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as TextField

            if (question != other.question) return false

            return true
        }

        override fun hashCode(): Int {
            return question.hashCode()
        }
    }

    class VerticalTextOptions(question: String, options: List<String>) : CardPracticeModel {
        var question: String by mutableStateOf(question)
        var options: List<String> by mutableStateOf(options)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as VerticalTextOptions

            if (question != other.question) return false
            if (options != other.options) return false

            return true
        }

        override fun hashCode(): Int {
            var result = question.hashCode()
            result = 31 * result + options.hashCode()
            return result
        }

    }
}