package com.clone.commerce.user.model.response

class SignUpResponseModel(
    val accessToken: String,
    val refreshToken: String,
    val userIdx: Long
) {
}
