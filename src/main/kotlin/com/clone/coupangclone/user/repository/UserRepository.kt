package com.clone.coupangclone.user.repository

import com.clone.coupangclone.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
}
