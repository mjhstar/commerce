package com.clone.commerce.common.support.extension

import com.clone.commerce.common.web.exception.BusinessException
import com.clone.commerce.common.web.exception.ErrorCode

inline fun <R> Boolean.isTrueThen(block: () -> R): R? {
    return if (this) block() else null
}

inline fun <R> Boolean.isFalseThen(block: () -> R): R? {
    return if (!this) block() else null
}

fun Boolean.isFalseThenThrow(errorCode: ErrorCode) {
    if (!this) throw BusinessException(errorCode)
}

fun Boolean.isTrueThenThrow(errorCode: ErrorCode) {
    if (this) throw BusinessException(errorCode)
}
