package com.clone.coupangclone.common.web.exception

import java.time.OffsetDateTime

class ErrorResponse(
    val timestamp: OffsetDateTime,
    val status: Int,
    val message: String?,
    val data: Any?
) {
}
