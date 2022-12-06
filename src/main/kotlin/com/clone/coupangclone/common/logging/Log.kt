package com.clone.coupangclone.common.logging

import com.clone.coupangclone.common.extension.isNullOrEmptyOrBlank
import com.clone.coupangclone.common.extension.toJson
import java.nio.charset.Charset
import javax.servlet.http.HttpServletRequest
import org.apache.commons.io.IOUtils

class Log {
    companion object: LoggingCompanion() {
        fun getIp(servlet: HttpServletRequest): String {
            var ip = servlet.getHeader("X-Forwarded-For")
            if (ip.isNullOrEmptyOrBlank()) {
                ip = servlet.getHeader("X-FORWARDED-FOR")
            }
            if (ip.isNullOrEmptyOrBlank()) {
                ip = servlet.getHeader("X-Real-IP")
            }
            if (ip.isNullOrEmptyOrBlank()) {
                ip = servlet.getHeader("X-REAL-IP")
            }
            if (ip.isNullOrEmptyOrBlank()) {
                ip = servlet.remoteAddr
            }
            return ip
        }

        fun getHeader(servlet: HttpServletRequest): String {
            val headerValues = mutableMapOf<String, String?>()
            headerValues["Referer"] = servlet.getHeader("Referer")
            headerValues["User-Agent"] = servlet.getHeader("User-Agent")
            headerValues["Authorization"] = servlet.getHeader("Authorization")
            headerValues["AuthorizeTime"] = servlet.getHeader("AuthorizeTime")
            return headerValues.filter { it.value != null }.toJson()
        }

        fun getRequestBody(servlet: HttpServletRequest): String {
            return try {
                var body = IOUtils.toString(servlet.inputStream, Charset.defaultCharset())
                if(body.isNullOrEmptyOrBlank()){
                    body = servlet.parameterMap.toJson()
                }
                if(body.length > 1000){
                    body = body.substring(0, 1000)
                }
                body
            } catch (e: Exception) {
                ""
            }
        }

        fun getLoggingParam(param: LoggingParam): Map<String, Any?>{
            val logParam = mutableMapOf<String, Any?>()
            logParam["userIdx"] = param.userIdx
            logParam["url"] = param.url
            logParam["header"] = param.header
            logParam["request"] = param.request
            logParam["error"] = param.error
            logParam["requestTime"] = param.requestTime
            logParam["responseTime"] = param.responseTime
            logParam["ip"] = param.ip
            return logParam.filter { it.value != null }
        }

        fun<T> success(data: T, param: LoggingParam): T{
            success(param)
            return data
        }


        fun success(param: LoggingParam){
            val logParam = getLoggingParam(param)
            logger.info(logParam.toJson())
        }


        fun error(param: LoggingParam) {
            param.responseTime = System.currentTimeMillis()
            val logParam: Map<String, Any?> = getLoggingParam(param)
            logger.error(logParam.toJson())
        }
    }
}
