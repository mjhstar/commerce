package com.clone.coupangclone.user.repository

import com.clone.coupangclone.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
}

fun UserRepository.createUser(user: User): User{
    return this.save(user)
}
