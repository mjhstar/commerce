package com.clone.coupangclone.user.model.response

class LoginResponse(
    val email: String,
    val userName: String,
    val refreshToken: String,
    val accessToken: String
) {
}
