package com.clone.commerce.user.model

import com.clone.commerce.common.support.extension.TimeUtils

class RefreshTokenParam(
    val email: String,
    val key: String,
    val createdAt: Long = TimeUtils.currentTimeMillis()
) {
}
