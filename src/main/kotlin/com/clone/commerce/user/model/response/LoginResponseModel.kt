package com.clone.commerce.user.model.response

class LoginResponseModel(
    val email: String,
    val userName: String,
    val userIdx: Long,
    val refreshToken: String,
    val accessToken: String
) {
}
