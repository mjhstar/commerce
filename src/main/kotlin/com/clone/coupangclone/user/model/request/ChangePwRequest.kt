package com.clone.coupangclone.user.model.request

import com.clone.coupangclone.common.config.security.Keys
import com.clone.coupangclone.common.extension.TimeUtils
import com.clone.coupangclone.common.extension.aesDecode
import com.clone.coupangclone.common.extension.aesEncode

class ChangePwRequest(
    val email: String,
    val name: String,
    private val phoneNumber: String,
    private val key: String,
    private val password: String,
    private val checkPassword: String
) {
    fun checkPassword(): Boolean {
        return this.password == checkPassword
    }

    fun getPW(): String {
        return this.password.aesEncode()
    }

    fun checkKey(): Boolean {
        val decodedKey = key.aesDecode()
        val time = decodedKey.substringAfter(Keys.FIND_PASSWORD_KEY).toLong()
        val currentTime = TimeUtils.currentTimeMillis()
        return (currentTime - time) / 1000 <= 180 && decodedKey.contains(Keys.FIND_PASSWORD_KEY)
    }

    fun getPH(): String {
        return this.phoneNumber.replace("-", "")
    }
}
