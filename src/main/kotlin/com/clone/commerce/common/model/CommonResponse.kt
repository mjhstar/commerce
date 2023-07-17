package com.clone.commerce.common.model

import org.springframework.http.HttpStatus
import java.time.OffsetDateTime

class CommonResponse<T>(
    val status: Int,
    val message: String?,
    val data: T?,
    val requestTime: OffsetDateTime,
    val responseTime: OffsetDateTime = OffsetDateTime.now()
) {
    companion object {
        fun <T> success(data: T, requestTime: OffsetDateTime): CommonResponse<T> {
            return CommonResponse(
                status = HttpStatus.OK.value(),
                message = HttpStatus.OK.reasonPhrase,
                data = data,
                requestTime = requestTime
            )
        }

    }
}
