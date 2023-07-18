package com.clone.commerce.user.controller

import com.clone.commerce.common.extension.TimeUtils
import com.clone.commerce.common.model.CommonResponse
import com.clone.commerce.user.model.request.*
import com.clone.commerce.user.model.response.*
import com.clone.commerce.user.service.UserService
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/user/{apiVersion}")
class UserController(
    private val userService: UserService
) {
    companion object {
        private const val SIGNUP_URL = "/signup"
        private const val SIGNOUT_URL = "/signout"
        private const val LOGIN_URL = "/login"
        private const val LOGOUT_URL = "/logout"
        private const val FIND_ID_URL = "/find/id"
        private const val FIND_PW_URL = "/find/password"
        private const val CHANGE_PW_URL = "/change/password"
        private const val REFRESH_TOKEN_URL = "/refresh-token"
    }

    @PostMapping(SIGNUP_URL)
    fun signUp(
        servlet: HttpServletRequest,
        @PathVariable apiVersion: String,
        @RequestBody request: SignUpRequest
    ): CommonResponse<SignUpResponse> {
        val requestTime = TimeUtils.currentTimeMillis()
        val responseModel = userService.signUp(request.toModel())
        return CommonResponse.success(SignUpResponse.createBy(responseModel), requestTime)
    }

    @PostMapping(LOGIN_URL)
    fun login(
        servlet: HttpServletRequest,
        @PathVariable apiVersion: String,
        @RequestBody request: LoginRequest
    ): CommonResponse<LoginResponse> {
        val requestTime = TimeUtils.currentTimeMillis()
        val responseModel = userService.login(request.toModel())
        return CommonResponse.success(LoginResponse.createBy(responseModel), requestTime)
    }

    @PostMapping(REFRESH_TOKEN_URL)
    fun getRefreshToken(
        servlet: HttpServletRequest,
        @PathVariable apiVersion: String,
        @RequestBody request: RefreshTokenRequest
    ): CommonResponse<RefreshTokenResponse> {
        val requestTime = TimeUtils.currentTimeMillis()
        val responseModel = userService.getRefreshToken(request.toModel())
        return CommonResponse.success(RefreshTokenResponse.createBy(responseModel), requestTime)
    }

    @PostMapping(FIND_ID_URL)
    fun findId(
        servlet: HttpServletRequest,
        @PathVariable apiVersion: String,
        @RequestBody request: FindIdRequest
    ): CommonResponse<FindIdResponse> {
        val requestTime = TimeUtils.currentTimeMillis()
        val responseModel = userService.findId(request.toModel())
        return CommonResponse.success(FindIdResponse.createBy(responseModel), requestTime)
    }

    @PostMapping(FIND_PW_URL)
    fun findPw(
        servlet: HttpServletRequest,
        @PathVariable apiVersion: String,
        @RequestBody request: FindPwRequest
    ): CommonResponse<FindPwResponse> {
        val requestTime = TimeUtils.currentTimeMillis()
        val responseModel = userService.findPw(request.toModel())
        return CommonResponse.success(FindPwResponse.createBy(responseModel), requestTime)
    }

    @PutMapping(CHANGE_PW_URL)
    fun changePw(
        servlet: HttpServletRequest,
        @PathVariable apiVersion: String,
        @RequestBody request: ChangePwRequest
    ): CommonResponse<ChangePwResponse> {
        val requestTime = TimeUtils.currentTimeMillis()
        val responseModel = userService.changePw(request.toModel())
        return CommonResponse.success(ChangePwResponse.createBy(responseModel), requestTime)
    }
}
