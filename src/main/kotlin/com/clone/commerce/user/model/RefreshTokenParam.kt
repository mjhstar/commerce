package com.clone.commerce.user.model

import com.clone.commerce.common.extension.TimeUtils

class RefreshTokenParam(
    val email: String,
    val createdAt: Long = TimeUtils.currentTimeMillis()
) {
}
