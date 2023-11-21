package com.clone.commerce.user.model.dto

import com.clone.commerce.user.entity.User
import com.clone.commerce.user.enums.UserType

class UserDto(
    val userIdx: Long,
    val type: UserType,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val terms: String,
    val refreshToken: String,
    val createdAt: Long
) {

    companion object {
        fun createBy(user: User): UserDto {
            return UserDto(
                userIdx = user.userIdx,
                type = user.type,
                name = user.name,
                email = user.email,
                phoneNumber = user.phoneNumber,
                terms = user.terms,
                refreshToken = user.refreshToken,
                createdAt = user.createdAt
            )
        }
    }
}