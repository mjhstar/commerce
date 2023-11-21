package com.clone.commerce.user.service

import com.clone.commerce.common.config.security.Keys
import com.clone.commerce.common.support.extension.TimeUtils
import com.clone.commerce.common.support.extension.aesEncode
import com.clone.commerce.common.support.extension.isValidEmail
import com.clone.commerce.common.web.exception.BusinessException
import com.clone.commerce.common.web.exception.ErrorCode
import com.clone.commerce.user.model.AccessTokenParam
import com.clone.commerce.user.model.request.*
import com.clone.commerce.user.model.response.*
import com.clone.commerce.user.provider.JwtTokenProvider
import com.clone.commerce.user.provider.UserProvider
import org.springframework.stereotype.Service

@Service
class UserService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userProvider: UserProvider
) {
    fun signUp(request: SignUpRequestModel): SignUpResponseModel {
        if (!request.email.isValidEmail()) {
            throw BusinessException(ErrorCode.INVALID_EMAIL)
        }
        if (userProvider.isExistUser(request.email)) {
            throw BusinessException(ErrorCode.EXIST_EMAIL)
        }
        val key = TimeUtils.currentTimeMillis().toString()
        val refreshToken = jwtTokenProvider.createRefreshToken(request.toRefreshTokenParam(key))
        val user = userProvider.createUser(request, refreshToken)
        val accessToken = jwtTokenProvider.createAccessToken(request.toAccessTokenParam(user.userIdx, key))

        return SignUpResponseModel(
            accessToken = accessToken,
            refreshToken = refreshToken,
            userIdx = user.userIdx
        )
    }

    fun login(request: RefreshTokenRequestModel): RefreshTokenResponseModel {
        if (Keys.CLIENT_KEY != request.clientKey)
            throw BusinessException(ErrorCode.MISMATCH_TOKEN_ISSUER)
        val user = userProvider.getRefreshToken(request.email, request.password)
        return RefreshTokenResponseModel(
            refreshToken = user.refreshToken,
            email = user.email,
            userIdx = user.userIdx
        )
    }

    fun getAccessToken(request: LoginRequestModel): LoginResponseModel {
        if (Keys.CLIENT_KEY != request.clientKey)
            throw BusinessException(ErrorCode.MISMATCH_TOKEN_ISSUER)
        val user = userProvider.getUser(request.email)
        if (user.refreshToken != request.refreshToken) {
            throw BusinessException(ErrorCode.INVALID_USER_TOKEN)
        }
        val key = jwtTokenProvider.getKey(user.refreshToken) ?: throw BusinessException(ErrorCode.INVALID_USER_TOKEN)
        val accessToken = jwtTokenProvider.createAccessToken(
            AccessTokenParam(
                userIdx = user.userIdx,
                key = key,
                type = user.type,
                email = user.email
            )
        )
        return LoginResponseModel(
            email = user.email,
            userName = user.name,
            userIdx = user.userIdx,
            refreshToken = user.refreshToken,
            accessToken = accessToken
        )
    }

    fun findId(request: FindIdRequestModel): FindIdResponseModel {
        val user = userProvider.getUser(request.name, request.getPH())
        return FindIdResponseModel(email = user.email)
    }

    fun findPw(request: FindPwRequestModel): FindPwResponseModel {
        val user = userProvider.getUser(request.name, request.getPH(), request.email)
        return FindPwResponseModel(
            name = user.name,
            email = user.email,
            phoneNumber = user.phoneNumber,
            key = (Keys.FIND_PASSWORD_KEY + TimeUtils.currentTimeMillis().toString()).aesEncode()
        )
    }

    fun changePw(request: ChangePwRequestModel): ChangePwResponseModel {
        if (!request.checkKey()) {
            throw BusinessException(ErrorCode.INVALID_REQUEST)
        }
        if (!request.checkPassword()) {
            throw BusinessException(ErrorCode.MISMATCH_PASSWORD)
        }
        val user = userProvider.changePassword(request)
        return ChangePwResponseModel(
            email = user.email,
            complete = true
        )
    }
}
