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

        //        fun <T> error(e: Throwable, data: T?): CommonResponse<T> {
        //            return error(e, data, TimeUtils.currentTimeMillis())
        //        }
        //
        //        fun <T> error(e: Throwable, requestTime: Long): CommonResponse<T> {
        //            return error(e, null, requestTime)
        //        }
        //
        //        fun <T> error(e: Throwable, data: T?, requestTime: Long): CommonResponse<T> {
        //            return if (e is BusinessException) {
        //                CommonResponse(
        //                    status = e.code,
        //                    message = e.message,
        //                    data = data,
        //                    requestTime = requestTime
        //                )
        //            } else if (e is MissingServletRequestParameterException || e is MissingServletRequestPartException) {
        //                CommonResponse(
        //                    status = HttpStatus.BAD_REQUEST.value().toString(),
        //                    message = "BadRequest",
        //                    data = data,
        //                    requestTime = requestTime
        //                )
        //            } else {
        //                CommonResponse(
        //                    status = HttpStatus.INTERNAL_SERVER_ERROR.value().toString(),
        //                    message = "Interval Server Error",
        //                    data = data,
        //                    requestTime = requestTime
        //                )
        //            }
        //        }
    }
}
