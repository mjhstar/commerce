package com.clone.coupangclone.common.web

import com.clone.coupangclone.common.config.rx.SchedulerFactory
import com.clone.coupangclone.common.model.CommonResponse
import com.clone.coupangclone.common.util.ResponseUtil
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
        val response = mapOf(Pair("serviceName", "coupang-clone"))
        return ResponseUtil.result(
            response = Observable.just(response),
            servlet = servlet,
            scheduler = SchedulerFactory.GLOBAL.scheduler
        )
    }
}
