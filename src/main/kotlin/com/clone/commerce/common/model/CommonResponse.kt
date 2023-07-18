package com.clone.commerce.common.model

import org.springframework.http.HttpStatus
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId

class CommonResponse<T>(
    val status: Int,
    val message: String?,
    val data: T?,
    val requestTime: OffsetDateTime,
    val responseTime: OffsetDateTime = OffsetDateTime.now()
) {
    companion object {
        fun <T> success(data: T, requestTime: Long): CommonResponse<T> {
            val instant = Instant.ofEpochMilli(requestTime)
            return CommonResponse(
                status = HttpStatus.OK.value(),
                message = HttpStatus.OK.reasonPhrase,
                data = data,
                requestTime = OffsetDateTime.ofInstant(instant, ZoneId.systemDefault())
            )
        }

    }
}
