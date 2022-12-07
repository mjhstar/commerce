package com.clone.coupangclone.user.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userIdx: Long = 0,
    val name: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val terms: String
) {
}
