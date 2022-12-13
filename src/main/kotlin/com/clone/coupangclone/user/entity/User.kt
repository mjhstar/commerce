package com.clone.coupangclone.user.entity

import com.clone.coupangclone.common.extension.TimeUtils
import com.clone.coupangclone.user.model.request.SignUpRequest
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "USER")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userIdx: Long = 0,
    val name: String,
    val email: String,
    var password: String,
    val phoneNumber: String,
    val refreshToken: String,
    val terms: String,
    val createdAt: Long = TimeUtils.currentTimeMillis(),
    var updatedAt: Long? = null
) {
    companion object {
        fun createBy(request: SignUpRequest, token: String): User {
            return User(
                name = request.name,
                email = request.email,
                password = request.getPW(),
                phoneNumber = request.getPH(),
                refreshToken = token,
                terms = request.getTerms()
            )
        }
    }
}
