package com.clone.commerce.common.model

import com.clone.commerce.common.support.extension.TimeUtils
import com.clone.commerce.common.support.extension.toOffsetDateTime
import org.springframework.http.HttpStatus
import java.time.OffsetDateTime

class CommonResponse<T>(
    val status: Int,
    val message: String?,
    val data: T?,
    val requestTime: OffsetDateTime,
    val responseTime: OffsetDateTime
) {
    companion object {
        fun <T> success(data: T, requestTime: Long): CommonResponse<T> {
            return CommonResponse(
                status = HttpStatus.OK.value(),
                message = HttpStatus.OK.reasonPhrase,
                data = data,
                requestTime = requestTime.toOffsetDateTime(),
                responseTime = TimeUtils.currentOffsetDateTime()
            )
        }

    }
}
