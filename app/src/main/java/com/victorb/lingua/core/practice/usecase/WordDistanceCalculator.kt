package com.victorb.lingua.core.practice.usecase

import javax.inject.Inject

// https://www.baeldung.com/java-levenshtein-distance
class WordDistanceCalculator @Inject constructor() {

    fun calculate(wordA: String, wordB: String): Int {
        val dp = Array(wordA.length + 1) {
            IntArray(
                wordB.length + 1
            )
        }

        for (i in 0..wordA.length) {
            for (j in 0..wordB.length) {
                if (i == 0) {
                    dp[i][j] = j
                } else if (j == 0) {
                    dp[i][j] = i
                } else {
                    dp[i][j] = min(
                        dp[i - 1][j - 1] + costOfSubstitution(wordA[i - 1], wordB[j - 1]),
                        dp[i - 1][j] + 1,
                        dp[i][j - 1] + 1
                    )
                }
            }
        }

        return dp[wordA.length][wordB.length]
    }

    private fun costOfSubstitution(a: Char, b: Char): Int {
        return if (a == b) 0 else 1
    }

    private fun min(vararg numbers: Int) = numbers.toList().minOrNull() ?: Int.MAX_VALUE

}