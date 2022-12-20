package com.clone.commerce.common.web

import com.clone.commerce.common.config.rx.SchedulerFactory
import com.clone.commerce.common.model.CommonResponse
import com.clone.commerce.common.util.ResponseUtil
import io.reactivex.rxjava3.core.Observable
import javax.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestAlive {
    @GetMapping("/test-alive")
    fun testAlive(
        servlet: HttpServletRequest
    ): Observable<CommonResponse> {
        val response = mapOf(Pair("serviceName", "commerce"))
        return ResponseUtil.result(
            response = Observable.just(response),
            servlet = servlet,
            scheduler = SchedulerFactory.GLOBAL.scheduler
        )
    }
}
