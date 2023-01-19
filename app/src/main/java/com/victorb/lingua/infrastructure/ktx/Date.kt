package com.victorb.lingua.infrastructure.ktx

import java.util.*

private const val ONE_HOUR_IN_MILLIS = 60 * 60 * 1000

fun Date.plusHours(hours: Int) = Date(this.time + ONE_HOUR_IN_MILLIS * hours)