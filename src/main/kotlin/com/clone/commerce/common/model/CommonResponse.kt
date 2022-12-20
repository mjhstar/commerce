package com.clone.commerce.common.model

import com.clone.commerce.common.extension.TimeUtils
import com.clone.commerce.common.web.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.multipart.support.MissingServletRequestPartException

class CommonResponse(
    val status: String,
    val message: String?,
    val data: Any?,
    val requestTime: Long,
    val responseTime: Long = TimeUtils.currentTimeMillis()
) {
    companion object {
        fun <T> success(data: T, requestTime: Long): CommonResponse {
            return if (data is CommonResponse) {
                CommonResponse(
                    status = HttpStatus.OK.value().toString(),
                    message = HttpStatus.OK.reasonPhrase,
                    data = data.data,
                    requestTime = requestTime
                )
            } else {
                CommonResponse(
                    status = HttpStatus.OK.value().toString(),
                    message = HttpStatus.OK.reasonPhrase,
                    data = data,
                    requestTime = requestTime
                )
            }
        }

        fun error(e: Throwable, data: Any?): CommonResponse {
            return error(e, data, TimeUtils.currentTimeMillis())
        }

        fun error(e: Throwable, requestTime: Long): CommonResponse {
            return error(e, null, requestTime)
        }

        fun error(e: Throwable, data: Any?, requestTime: Long): CommonResponse {
            return if (e is BusinessException) {
                CommonResponse(
                    status = e.code,
                    message = e.message,
                    data = data,
                    requestTime = requestTime
                )
            } else if (e is MissingServletRequestParameterException || e is MissingServletRequestPartException) {
                CommonResponse(
                    status = HttpStatus.BAD_REQUEST.value().toString(),
                    message = "BadRequest",
                    data = data,
                    requestTime = requestTime
                )
            } else {
                CommonResponse(
                    status = HttpStatus.INTERNAL_SERVER_ERROR.value().toString(),
                    message = "Interval Server Error",
                    data = data,
                    requestTime = requestTime
                )
            }
        }
    }
}
