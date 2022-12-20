package com.clone.commerce.user.controller

import com.clone.commerce.common.config.rx.SchedulerFactory
import com.clone.commerce.common.model.CommonResponse
import com.clone.commerce.common.util.ResponseUtil
import com.clone.commerce.user.model.request.ChangePwRequest
import com.clone.commerce.user.model.request.FindIdRequest
import com.clone.commerce.user.model.request.FindPwRequest
import com.clone.commerce.user.model.request.LoginRequest
import com.clone.commerce.user.model.request.RefreshTokenRequest
import com.clone.commerce.user.model.request.SignUpRequest
import com.clone.commerce.user.service.UserService
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
        private const val FIND_ID_URL = "/v1/find/id"
        private const val FIND_PW_URL = "/v1/find/password"
        private const val CHANGE_PW_URL = "/v1/change/password"
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

    @PostMapping(FIND_ID_URL)
    fun findId(
        servlet: HttpServletRequest,
        @RequestBody request: FindIdRequest
    ): Observable<CommonResponse>{
        return ResponseUtil.result(
            response = Observable.fromCallable { userService.findId(request) },
            scheduler = SchedulerFactory.GLOBAL.scheduler,
            request = request,
            servlet = servlet
        )
    }

    @PostMapping(FIND_PW_URL)
    fun findPw(
        servlet: HttpServletRequest,
        @RequestBody request: FindPwRequest
    ): Observable<CommonResponse>{
        return ResponseUtil.result(
            response = Observable.fromCallable { userService.findPw(request) },
            scheduler = SchedulerFactory.GLOBAL.scheduler,
            request = request,
            servlet = servlet
        )
    }

    @PostMapping(CHANGE_PW_URL)
    fun changePw(
        servlet: HttpServletRequest,
        @RequestBody request: ChangePwRequest
    ): Observable<CommonResponse>{
        return ResponseUtil.result(
            response = Observable.fromCallable { userService.changePw(request) },
            scheduler = SchedulerFactory.GLOBAL.scheduler,
            request = request,
            servlet = servlet
        )
    }
}
