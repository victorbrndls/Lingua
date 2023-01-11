package com.victorb.lingua.infrastructure.ktx

fun <T> List<T>.replaceOrAdd(existing: (T) -> Boolean, new: T): List<T> {
    val existingIdx = indexOfFirst { existing(it) }
    if (existingIdx == -1) return this + new

    return mapIndexed { idx, element ->
        if (existingIdx == idx) new else element
    }
}