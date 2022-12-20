package com.clone.commerce.user.repository

import com.clone.commerce.common.web.exception.BusinessException
import com.clone.commerce.common.web.exception.ErrorCode
import com.clone.commerce.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
    fun findByNameAndPhoneNumber(name: String, phoneNumber: String): User?
    fun findByNameAndPhoneNumberAndEmail(name: String, phoneNumber: String, email: String): User?
}

fun UserRepository.createUser(user: User): User{
    return this.save(user)
}

fun UserRepository.findUser(name: String, phoneNumber: String): User{
    return this.findByNameAndPhoneNumber(name, phoneNumber)
        ?: throw BusinessException(ErrorCode.NOT_EXIST_USER)
}

fun UserRepository.findUser(name: String, phoneNumber: String, email: String): User{
    return this.findByNameAndPhoneNumberAndEmail(name, phoneNumber, email)
        ?: throw BusinessException(ErrorCode.NOT_EXIST_USER)
}
