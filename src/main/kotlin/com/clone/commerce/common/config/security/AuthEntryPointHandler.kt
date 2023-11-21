package com.clone.commerce.common.config.security

import com.clone.commerce.common.logging.Log
import com.clone.commerce.common.logging.LoggingParam
import com.clone.commerce.common.support.extension.TimeUtils
import com.clone.commerce.common.support.extension.isNullOrEmptyOrBlank
import com.clone.commerce.common.support.extension.toJson
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthEntryPointHandler : AuthenticationEntryPoint {
    @Throws(Exception::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val param = LoggingParam().error(authException).servlet(request)
        if (param.request.isNullOrEmptyOrBlank()) {
            Log.error(param)
        }
        response.apply {
            this.status = HttpStatus.UNAUTHORIZED.value()
            this.contentType = MediaType.APPLICATION_JSON_VALUE
            this.characterEncoding = StandardCharsets.UTF_8.toString()
        }
        val data = mutableMapOf<String, Any?>()
        data["status"] = 999
        data["message"] = "Invalid Access"
        data["path"] = request.requestURI
        data["requestTime"] = try {
            request.getHeader("AuthorizeTime").toLong() * 1000
        } catch (e: Exception) {
            TimeUtils.currentTimeMillis()
        }
        data["responseTime"] = TimeUtils.currentTimeMillis()
        response.writer.write(data.toJson())
    }
}
