package com.clone.commerce.common.model

import com.clone.commerce.common.extension.TimeUtils
import org.springframework.http.HttpStatus

class CommonResponse<T>(
    val status: Int,
    val message: String?,
    val data: T?,
    val requestTime: Long,
    val responseTime: Long = TimeUtils.currentTimeMillis()
) {
    companion object {
        fun <T> success(data: T): CommonResponse<T> {
            return CommonResponse(
                status = HttpStatus.OK.value(),
                message = HttpStatus.OK.reasonPhrase,
                data = data,
                requestTime = TimeUtils.currentTimeMillis()
            )
        }

    }
}
