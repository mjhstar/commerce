package com.clone.commerce.user.service

import com.clone.commerce.common.config.security.Keys
import com.clone.commerce.common.extension.TimeUtils
import com.clone.commerce.common.extension.aesDecode
import com.clone.commerce.common.extension.aesEncode
import com.clone.commerce.common.extension.isValidEmail
import com.clone.commerce.common.web.exception.BusinessException
import com.clone.commerce.common.web.exception.ErrorCode
import com.clone.commerce.user.entity.User
import com.clone.commerce.user.model.AccessTokenParam
import com.clone.commerce.user.model.request.ChangePwRequestModel
import com.clone.commerce.user.model.request.FindIdRequestModel
import com.clone.commerce.user.model.request.FindPwRequestModel
import com.clone.commerce.user.model.request.LoginRequestModel
import com.clone.commerce.user.model.request.RefreshTokenRequestModel
import com.clone.commerce.user.model.request.SignUpRequestModel
import com.clone.commerce.user.model.response.ChangePwResponseModel
import com.clone.commerce.user.model.response.FindIdResponseModel
import com.clone.commerce.user.model.response.FindPwResponseModel
import com.clone.commerce.user.model.response.LoginResponseModel
import com.clone.commerce.user.model.response.RefreshTokenResponseModel
import com.clone.commerce.user.model.response.SignUpResponseModel
import com.clone.commerce.user.repository.UserRepository
import com.clone.commerce.user.repository.createUser
import com.clone.commerce.user.repository.findUser
import org.springframework.stereotype.Service

@Service
class UserService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository
) {
    fun signUp(request: SignUpRequestModel): SignUpResponseModel {
        if (!request.email.isValidEmail()) {
            throw BusinessException(ErrorCode.INVALID_EMAIL)
        }
        if (userRepository.existsByEmail(request.email)) {
            throw BusinessException(ErrorCode.EXIST_EMAIL)
        }

        val refreshToken = jwtTokenProvider.createRefreshToken(request.toRefreshTokenParam())
        val user = userRepository.createUser(User.createBy(request, refreshToken))
        val accessToken = jwtTokenProvider.createAccessToken(request.toAccessTokenParam(user.userIdx))

        return SignUpResponseModel(
            accessToken = accessToken,
            refreshToken = refreshToken,
            userIdx = user.userIdx
        )
    }

    fun getRefreshToken(request: RefreshTokenRequestModel): RefreshTokenResponseModel {
        if (Keys.CLIENT_KEY != request.clientKey)
            throw BusinessException(ErrorCode.MISMATCH_TOKEN_ISSUER)
        val user = userRepository.findByEmail(request.email)
            ?: throw BusinessException(ErrorCode.INVALID_USER_EMAIL)
        val password = user.password.aesDecode()
        if (password != request.password) {
            throw BusinessException(ErrorCode.INVALID_USER_PASSWORD)
        }
        return RefreshTokenResponseModel(
            refreshToken = user.refreshToken,
            email = user.email,
            userIdx = user.userIdx
        )
    }

    fun login(request: LoginRequestModel): LoginResponseModel {
        if (Keys.CLIENT_KEY != request.clientKey)
            throw BusinessException(ErrorCode.MISMATCH_TOKEN_ISSUER)
        val user = userRepository.findByEmail(request.email) ?: throw BusinessException(ErrorCode.INVALID_USER_EMAIL)
        if (user.refreshToken != request.refreshToken) {
            throw BusinessException(ErrorCode.INVALID_USER_TOKEN)
        }
        val accessToken = jwtTokenProvider.createAccessToken(
            AccessTokenParam(
                userIdx = user.userIdx,
                type = user.type,
                email = user.email
            )
        )
        return LoginResponseModel(
            email = user.email,
            userName = user.name,
            refreshToken = user.refreshToken,
            accessToken = accessToken
        )
    }

    fun findId(request: FindIdRequestModel): FindIdResponseModel {
        val user = userRepository.findUser(request.name, request.getPH())
        return FindIdResponseModel(email = user.email)
    }

    fun findPw(request: FindPwRequestModel): FindPwResponseModel {
        val user = userRepository.findUser(
            name = request.name,
            phoneNumber = request.getPH(),
            email = request.email
        )
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
        val user = userRepository.findUser(
            name = request.name,
            email = request.email,
            phoneNumber = request.getPH()
        ).apply {
            this.password = request.getPW()
            this.updatedAt = TimeUtils.currentTimeMillis()
        }
        userRepository.save(user)
        return ChangePwResponseModel(
            email = user.email,
            complete = true
        )
    }
}
