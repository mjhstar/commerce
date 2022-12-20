package com.clone.commerce.user.model.response

class RefreshTokenResponse(
    val refreshToken: String,
    val email: String,
    val userIdx: Long
) {
}
