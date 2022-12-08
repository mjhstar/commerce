package com.clone.coupangclone.common.config.security

import com.clone.coupangclone.common.extension.TimeUtils
import com.clone.coupangclone.common.extension.isNullOrEmptyOrBlank
import com.clone.coupangclone.common.extension.toJson
import com.clone.coupangclone.common.logging.Log
import com.clone.coupangclone.common.logging.LoggingParam
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class AccessDeniedHandler: AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        val param = LoggingParam().error(accessDeniedException).servlet(request)
        if(param.request.isNullOrEmptyOrBlank()){
            Log.error(param)
        }
        response.apply {
            this.status = HttpStatus.UNAUTHORIZED.value()
            this.contentType = MediaType.APPLICATION_JSON_VALUE
            this.characterEncoding = StandardCharsets.UTF_8.toString()
        }
        val data = mutableMapOf<String, Any>()
        data["status"] = 999
        data["message"] = "Invalid Access"
        data["path"] = request.requestURI
        data["requestTime"] = request.getHeader("AuthorizeTime").toLong() * 1000
        data["responseTime"] = TimeUtils.currentTimeMillis()
        response.writer.write(data.toJson())
    }
}
