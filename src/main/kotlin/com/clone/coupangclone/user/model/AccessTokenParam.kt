package com.clone.coupangclone.user.model

import com.clone.coupangclone.common.extension.TimeUtils

class AccessTokenParam(
    val userIdx: Long,
    val email: String,
    val createdAt: Long = TimeUtils.currentTimeMillis()
) {
}
