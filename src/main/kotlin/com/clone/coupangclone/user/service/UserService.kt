package com.clone.coupangclone.user.service

import com.clone.coupangclone.common.config.security.Keys
import com.clone.coupangclone.common.extension.aesDecode
import com.clone.coupangclone.common.extension.isValidEmail
import com.clone.coupangclone.common.web.exception.BusinessException
import com.clone.coupangclone.common.web.exception.ErrorCode
import com.clone.coupangclone.user.entity.User
import com.clone.coupangclone.user.model.AccessTokenParam
import com.clone.coupangclone.user.model.request.LoginRequest
import com.clone.coupangclone.user.model.request.RefreshTokenRequest
import com.clone.coupangclone.user.model.request.SignUpRequest
import com.clone.coupangclone.user.model.response.LoginResponse
import com.clone.coupangclone.user.model.response.RefreshTokenResponse
import com.clone.coupangclone.user.model.response.SignUpResponse
import com.clone.coupangclone.user.repository.UserRepository
import com.clone.coupangclone.user.repository.createUser
import org.springframework.stereotype.Service

@Service
class UserService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository
) {
    fun signUp(request: SignUpRequest): SignUpResponse {
        if (!request.email.isValidEmail()) {
            throw BusinessException(ErrorCode.INVALID_EMAIL)
        }
        if (userRepository.existsByEmail(request.email)) {
            throw BusinessException(ErrorCode.EXIST_EMAIL)
        }

        val refreshToken = jwtTokenProvider.createRefreshToken(request.toRefreshTokenParam())
        val user = userRepository.createUser(User.createBy(request, refreshToken))

        val accessToken = jwtTokenProvider.createAccessToken(request.toAccessTokenParam(user.userIdx))
        return SignUpResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            userIdx = user.userIdx
        )
    }

    fun getRefreshToken(request: RefreshTokenRequest): RefreshTokenResponse {
        if (Keys.CLIENT_KEY != request.clientKey)
            throw BusinessException(ErrorCode.MISMATCH_TOKEN_ISSUER)
        val user = userRepository.findByEmail(request.email)
            ?: throw BusinessException(ErrorCode.INVALID_USER_EMAIL)
        val password = user.password.aesDecode()
        if (password != request.password) {
            throw BusinessException(ErrorCode.INVALID_USER_PASSWORD)
        }
        return RefreshTokenResponse(
            refreshToken = user.refreshToken,
            email = user.email,
            userIdx = user.userIdx
        )
    }

    fun login(request: LoginRequest): LoginResponse {
        if (Keys.CLIENT_KEY != request.clientKey)
            throw BusinessException(ErrorCode.MISMATCH_TOKEN_ISSUER)
        val user = userRepository.findByEmail(request.email) ?: throw BusinessException(ErrorCode.INVALID_USER_EMAIL)
        if (user.refreshToken != request.refreshToken) {
            throw BusinessException(ErrorCode.INVALID_USER_TOKEN)
        }
        val accessToken = jwtTokenProvider.createAccessToken(AccessTokenParam(user.userIdx, user.email))
        return LoginResponse(
            email = user.email,
            userName = user.name,
            refreshToken = user.refreshToken,
            accessToken = accessToken
        )
    }
}
