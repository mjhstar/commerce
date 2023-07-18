package com.clone.commerce.user.provider

import com.clone.commerce.common.extension.TimeUtils
import com.clone.commerce.common.extension.aesDecode
import com.clone.commerce.common.web.exception.BusinessException
import com.clone.commerce.common.web.exception.ErrorCode
import com.clone.commerce.user.entity.User
import com.clone.commerce.user.enums.UserType
import com.clone.commerce.user.model.dto.UserDto
import com.clone.commerce.user.model.request.ChangePwRequestModel
import com.clone.commerce.user.model.request.SignUpRequestModel
import com.clone.commerce.user.repository.UserRepository
import com.clone.commerce.user.repository.createUser
import com.clone.commerce.user.repository.findUser
import org.springframework.stereotype.Component

@Component
class UserProvider(
    private val userRepository: UserRepository
) {
    fun getUserType(userIdx: Long): UserType {
        return userRepository.findByUserIdx(userIdx)?.type ?: throw BusinessException(ErrorCode.NOT_EXIST_USER)
    }

    fun createUser(request: SignUpRequestModel, refreshToken: String): UserDto {
        val user = userRepository.createUser(User.createBy(request, refreshToken))
        return user.toDto()
    }

    fun existUser(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    fun getRefreshToken(email: String, inputPassword: String): UserDto {
        val user = userRepository.findByEmail(email)
            ?: throw BusinessException(ErrorCode.INVALID_USER_EMAIL)
        val password = user.password.aesDecode()
        if (password != inputPassword) {
            throw BusinessException(ErrorCode.INVALID_USER_PASSWORD)
        }
        return user.toDto()
    }

    fun getUser(email: String): UserDto {
        return userRepository.findByEmail(email)?.toDto() ?: throw BusinessException(ErrorCode.NOT_EXIST_USER)
    }

    fun getUser(name: String, phoneNumber: String): UserDto {
        return userRepository.findUser(name, phoneNumber).toDto()
    }

    fun getUser(name: String, phoneNumber: String, email: String): UserDto {
        return userRepository.findUser(name, phoneNumber, email).toDto()
    }

    fun changePassword(request: ChangePwRequestModel): UserDto {
        try {
            val user = userRepository.findUser(
                name = request.name,
                email = request.email,
                phoneNumber = request.getPH()
            ).apply {
                this.password = request.getPW()
                this.updatedAt = TimeUtils.currentTimeMillis()
            }
            return userRepository.save(user).toDto()

        }
        catch (e:Exception){
            throw BusinessException(ErrorCode.FAIL_CHANGE_PASSWORD)
        }
    }
}