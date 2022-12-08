package com.clone.coupangclone.user.model

import com.clone.coupangclone.common.extension.TimeUtils

class RefreshTokenParam(
    val email: String,
    val createdAt: Long = TimeUtils.currentTimeMillis()
) {
}
