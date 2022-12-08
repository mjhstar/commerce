package com.clone.coupangclone.user.controller

import com.clone.coupangclone.common.config.rx.SchedulerFactory
import com.clone.coupangclone.common.model.CommonResponse
import com.clone.coupangclone.common.util.ResponseUtil
import com.clone.coupangclone.user.model.request.LoginRequest
import com.clone.coupangclone.user.model.request.RefreshTokenRequest
import com.clone.coupangclone.user.model.request.SignUpRequest
import com.clone.coupangclone.user.service.UserService
import io.reactivex.rxjava3.core.Observable
import javax.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    companion object {
        private const val SIGNUP_URL = "/v1/signup"
        private const val SIGNOUT_URL = "/v1/signout"
        private const val LOGIN_URL = "/v1/login"
        private const val LOGOUT_URL = "/v1/logout"
        private const val REFRESH_TOKEN_URL = "/v1/refresh-token"
    }

    @PostMapping(SIGNUP_URL)
    fun signUp(
        servlet: HttpServletRequest,
        @RequestBody request: SignUpRequest
    ): Observable<CommonResponse> {
        return ResponseUtil.result(
            response = Observable.fromCallable { userService.signUp(request) },
            scheduler = SchedulerFactory.GLOBAL.scheduler,
            request = request,
            servlet = servlet
        )
    }

    @PostMapping(LOGIN_URL)
    fun login(
        servlet: HttpServletRequest,
        @RequestBody request: LoginRequest
    ): Observable<CommonResponse> {
        return ResponseUtil.result(
            response = Observable.fromCallable { userService.login(request) },
            scheduler = SchedulerFactory.GLOBAL.scheduler,
            request = request,
            servlet = servlet
        )
    }

    @PostMapping(REFRESH_TOKEN_URL)
    fun getRefreshToken(
        servlet: HttpServletRequest,
        @RequestBody request: RefreshTokenRequest
    ): Observable<CommonResponse> {
        return ResponseUtil.result(
            response = Observable.fromCallable { userService.getRefreshToken(request) },
            scheduler = SchedulerFactory.GLOBAL.scheduler,
            request = request,
            servlet = servlet
        )
    }
}
