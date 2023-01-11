package com.victorb.lingua.infrastructure.ktx

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Runs [action] regardless of the result
 */
@OptIn(ExperimentalContracts::class)
inline fun <T> Result<T>.onFinally(
    action: () -> Unit
): Result<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    action()
    return this
}
