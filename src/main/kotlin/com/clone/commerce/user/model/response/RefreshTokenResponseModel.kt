package com.clone.commerce.user.model.response

class RefreshTokenResponseModel(
    val refreshToken: String,
    val email: String,
    val userIdx: Long
) {
}
