package com.clone.commerce.common.web

import com.clone.commerce.common.config.rx.SchedulerFactory
import com.clone.commerce.common.extension.getUserIdx
import com.clone.commerce.common.model.CommonResponse
import com.clone.commerce.common.util.ResponseUtil
import com.clone.commerce.user.entity.User
import io.jsonwebtoken.Claims
import io.reactivex.rxjava3.core.Observable
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
    ): Observable<CommonResponse> {
        val userIdx = authentication.getUserIdx()
        val response = mapOf(Pair("serviceName", "commerce"))
        return ResponseUtil.result(
            response = Observable.just(response),
            servlet = servlet,
            scheduler = SchedulerFactory.GLOBAL.scheduler
        )
    }
}
