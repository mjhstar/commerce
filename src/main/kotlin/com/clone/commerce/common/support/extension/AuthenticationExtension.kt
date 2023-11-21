package com.clone.commerce.common.support.extension

import com.clone.commerce.common.web.exception.BusinessException
import com.clone.commerce.common.web.exception.ErrorCode
import com.clone.commerce.user.entity.User
import com.clone.commerce.user.enums.UserType
import org.springframework.security.core.Authentication

fun Authentication.getUserIdx(): Long {
    return try {
        (this.principal as User).userIdx
    } catch (e: Exception) {
        throw BusinessException(ErrorCode.INVALID_USER_TOKEN)
    }
}

fun Authentication.getType(): UserType {
    return try {
        (this.principal as User).type
    } catch (e: Exception) {
        throw BusinessException(ErrorCode.INVALID_USER_TOKEN)
    }
}

fun Authentication.getEmail(): String {
    return try {
        (this.principal as User).email
    } catch (e: Exception) {
        throw BusinessException(ErrorCode.INVALID_USER_TOKEN)
    }
}

fun Authentication.getId(): String {
    return try {
        (this.principal as User).email.getId()
    } catch (e: Exception) {
        throw BusinessException(ErrorCode.INVALID_USER_TOKEN)
    }
}

fun Authentication.getUserName(): String {
    return try {
        (this.principal as User).name
    } catch (e: Exception) {
        throw BusinessException(ErrorCode.INVALID_USER_TOKEN)
    }
}
