package com.clone.commerce.common.web

import com.clone.commerce.common.extension.getUserIdx
import com.clone.commerce.common.model.CommonResponse
import javax.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestAlive {
    @GetMapping("/test-alive")
    fun testAlive(
        servlet: HttpServletRequest,
        authentication: Authentication
    ): CommonResponse<Map<String, String>> {
        val userIdx = authentication.getUserIdx()
        val response = mapOf(Pair("serviceName", "commerce"))
        return CommonResponse.success(response)
    }
}
