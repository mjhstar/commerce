package com.clone.coupangclone.common.util

import com.clone.coupangclone.common.extension.TimeExtension
import com.clone.coupangclone.common.logging.Log
import com.clone.coupangclone.common.logging.LoggingParam
import com.clone.coupangclone.common.model.CommonResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import javax.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication

class ResponseUtil {
    companion object {
        fun <T> result(
            response: Observable<T>,
            authentication: Authentication,
            request: Any?,
            servlet: HttpServletRequest,
            scheduler: Scheduler
        ): Observable<CommonResponse> {
            val requestTime = TimeExtension.currentTime()
            val loggingParam = LoggingParam(
                request = request
            ).servlet(servlet)

            return response
                .map { CommonResponse.success(it, requestTime) }
                .doOnSubscribe { Log.success(it, loggingParam) }
                .doOnError { Log.error(loggingParam.error(it)) }
                .onErrorReturn { CommonResponse.error(it, requestTime) }
                .subscribeOn(scheduler)
                .observeOn(scheduler)
        }

        fun <T> result(
            response: Observable<T>,
            servlet: HttpServletRequest,
            scheduler: Scheduler
        ): Observable<CommonResponse> {
            val requestTime = TimeExtension.currentTime()
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
