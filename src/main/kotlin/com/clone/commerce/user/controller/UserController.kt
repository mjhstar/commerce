package com.clone.commerce.user.controller

import com.clone.commerce.common.model.CommonResponse
import com.clone.commerce.user.model.request.ChangePwRequest
import com.clone.commerce.user.model.request.FindIdRequest
import com.clone.commerce.user.model.request.FindPwRequest
import com.clone.commerce.user.model.request.LoginRequest
import com.clone.commerce.user.model.request.RefreshTokenRequest
import com.clone.commerce.user.model.request.SignUpRequest
import com.clone.commerce.user.model.response.ChangePwResponse
import com.clone.commerce.user.model.response.FindIdResponse
import com.clone.commerce.user.model.response.FindPwResponse
import com.clone.commerce.user.model.response.LoginResponse
import com.clone.commerce.user.model.response.RefreshTokenResponse
import com.clone.commerce.user.model.response.SignUpResponse
import com.clone.commerce.user.service.UserService
import javax.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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
    ): CommonResponse<SignUpResponse> {
        val responseModel = userService.signUp(request.toModel())
        return CommonResponse.success(SignUpResponse.createBy(responseModel))
    }

    @PostMapping(LOGIN_URL)
    fun login(
        servlet: HttpServletRequest,
        @RequestBody request: LoginRequest
    ): CommonResponse<LoginResponse> {
        val responseModel = userService.login(request.toModel())
        return CommonResponse.success(LoginResponse.createBy(responseModel))
    }

    @PostMapping(REFRESH_TOKEN_URL)
    fun getRefreshToken(
        servlet: HttpServletRequest,
        @RequestBody request: RefreshTokenRequest
    ): CommonResponse<RefreshTokenResponse> {
        val responseModel = userService.getRefreshToken(request.toModel())
        return CommonResponse.success(RefreshTokenResponse.createBy(responseModel))
    }

    @PostMapping(FIND_ID_URL)
    fun findId(
        servlet: HttpServletRequest,
        @RequestBody request: FindIdRequest
    ): CommonResponse<FindIdResponse> {
        val responseModel = userService.findId(request.toModel())
        return CommonResponse.success(FindIdResponse.createBy(responseModel))
    }

    @PostMapping(FIND_PW_URL)
    fun findPw(
        servlet: HttpServletRequest,
        @RequestBody request: FindPwRequest
    ): CommonResponse<FindPwResponse> {
        val responseModel = userService.findPw(request.toModel())
        return CommonResponse.success(FindPwResponse.createBy(responseModel))
    }

    @PutMapping(CHANGE_PW_URL)
    fun changePw(
        servlet: HttpServletRequest,
        @RequestBody request: ChangePwRequest
    ): CommonResponse<ChangePwResponse> {
        val responseModel = userService.changePw(request.toModel())
        return CommonResponse.success(ChangePwResponse.createBy(responseModel))
    }
}
