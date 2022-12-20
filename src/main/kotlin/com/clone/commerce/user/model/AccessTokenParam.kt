package com.clone.commerce.user.model

import com.clone.commerce.common.extension.TimeUtils
import com.clone.commerce.user.enums.UserType

class AccessTokenParam(
    val userIdx: Long,
    val type: UserType,
    val email: String,
    val createdAt: Long = TimeUtils.currentTimeMillis()
) {
}
