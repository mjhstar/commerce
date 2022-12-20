package com.clone.commerce.common.extension

inline fun <R> Boolean.isTrueThen(block: () -> R): R?{
    return if (this) block() else null
}

inline fun <R> Boolean.isFalseThen(block: () -> R): R? {
    return if (!this) block() else null
}

inline fun Boolean.isTrueThenThrow(block: () -> Throwable) {
    if (this) block()
}

inline fun Boolean.isFalseThenThrow(block: () -> Throwable) {
    if (!this) block()
}
