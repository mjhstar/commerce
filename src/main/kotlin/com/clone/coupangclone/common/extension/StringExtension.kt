package com.clone.coupangclone.common.extension

import java.util.regex.Pattern

const val EMAIL_PATTERN = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"
private const val DEFAULT_ENCODE_KEY = "!@#encode!@#"
fun String?.isValidEmail(): Boolean {
    if (this.isNullOrEmptyOrBlank()) {
        return false
    }
    if (Pattern.matches(EMAIL_PATTERN, this)) {
        return true
    }
    return false
}
