package com.clone.coupangclone.user.model.response

class RefreshTokenResponse(
    val refreshToken: String,
    val email: String,
    val userIdx: Long
) {
}
