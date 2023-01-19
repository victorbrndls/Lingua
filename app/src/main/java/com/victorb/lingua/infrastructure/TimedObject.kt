package com.victorb.lingua.infrastructure

import java.util.*

data class TimedObject<T>(val time: Date, val obj: T) {
    companion object {
        fun <T> ofNow(obj: T) = TimedObject(Date(), obj)
    }
}