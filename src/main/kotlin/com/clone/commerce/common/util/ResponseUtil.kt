package com.clone.commerce.common.util

import com.clone.commerce.common.extension.TimeUtils
import com.clone.commerce.common.logging.Log
import com.clone.commerce.common.logging.LoggingParam
import com.clone.commerce.common.model.CommonResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import javax.servlet.http.HttpServletRequest

class ResponseUtil {
    companion object {
        fun <T> result(
            response: Observable<T>,
            request: Any?,
            servlet: HttpServletRequest,
            scheduler: Scheduler
        ): Observable<CommonResponse> {
            val requestTime = TimeUtils.currentTimeMillis()
            val loggingParam = LoggingParam(
                request = request
            ).servlet(servlet)

            return response
                .map { CommonResponse.success(it, requestTime) }
                .doOnSubscribe { Log.success(it, loggingParam) }
                .doOnError { Log.error(loggingParam.error(it)) }
                .onErrorReturn { CommonResponse.error(it, request, requestTime) }
                .subscribeOn(scheduler)
                .observeOn(scheduler)
        }

        fun <T> result(
            response: Observable<T>,
            servlet: HttpServletRequest,
            scheduler: Scheduler
        ): Observable<CommonResponse> {
            val requestTime = TimeUtils.currentTimeMillis()
            val loggingParam = LoggingParam().servlet(servlet)
            return response
                .map { CommonResponse.success(it, requestTime) }
                .doOnSubscribe { Log.success(loggingParam) }
                .doOnError { Log.error(loggingParam.error(it)) }
                .onErrorReturn { CommonResponse.error(it, requestTime) }
                .subscribeOn(scheduler)
                .observeOn(scheduler)
        }
    }
}
