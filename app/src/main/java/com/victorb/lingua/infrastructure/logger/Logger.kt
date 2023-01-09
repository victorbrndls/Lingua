package com.victorb.lingua.infrastructure.logger

import android.util.Log

private const val TAG = "Lingua"

class Logger {

    companion object {
        fun d(message: String) {
            Log.d(TAG, message)
        }

        fun e(message: String, throwable: Throwable) {
            Log.e(TAG, message, throwable)
        }

        fun e(message: String) {
            Log.e(TAG, message)
        }
    }

}