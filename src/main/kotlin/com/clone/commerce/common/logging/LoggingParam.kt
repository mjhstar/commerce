package com.clone.commerce.common.logging

import com.clone.commerce.common.extension.isNullOrEmptyOrBlank
import javax.servlet.http.HttpServletRequest
import org.apache.commons.lang3.exception.ExceptionUtils

class LoggingParam(
    var userIdx: Long? = null,
    var url: String? = null,
    var header: String? = null,
    var request: Any? = null,
    var error: String? = null,
    var requestTime: Long? = null,
    var responseTime: Long? = null,
    var ip: String? = null
) {
    fun error(e: Throwable): LoggingParam{
        this.error = ExceptionUtils.getStackTrace(e)
        return this
    }

    fun servlet(servlet: HttpServletRequest): LoggingParam{
        this.ip = Log.getIp(servlet)
        this.url = servlet.requestURL.toString()
        this.header = Log.getHeader(servlet)
        if(this.request.isNullOrEmptyOrBlank()){
            this.request = Log.getRequestBody(servlet)
        }
        return this
    }
}
