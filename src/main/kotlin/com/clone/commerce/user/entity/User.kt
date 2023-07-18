package com.clone.commerce.user.entity

import com.clone.commerce.common.extension.TimeUtils
import com.clone.commerce.user.enums.UserType
import com.clone.commerce.user.model.dto.UserDto
import com.clone.commerce.user.model.request.SignUpRequestModel
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "USER")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userIdx: Long = 0,
    @Enumerated(EnumType.STRING)
    val type: UserType,
    val name: String,
    val email: String,
    var password: String,
    val phoneNumber: String,
    val refreshToken: String,
    val terms: String,
    val createdAt: Long = TimeUtils.currentTimeMillis(),
    var updatedAt: Long? = null
) {
    fun toDto(): UserDto {
        return UserDto.createBy(this)
    }
    companion object {
        fun createBy(request: SignUpRequestModel, token: String): User {
            return User(
                name = request.name,
                type = request.type,
                email = request.email,
                password = request.getPW(),
                phoneNumber = request.getPH(),
                refreshToken = token,
                terms = request.getTerms()
            )
        }
    }
}
