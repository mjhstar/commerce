package com.clone.commerce.user.model.response

class SignUpResponse(
    val accessToken: String,
    val refreshToken: String,
    val userIdx: Long
) {
}
