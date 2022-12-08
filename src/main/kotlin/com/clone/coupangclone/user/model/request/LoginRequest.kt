package com.clone.coupangclone.user.model.request

class LoginRequest(
    val email: String,
    val refreshToken: String,
    val clientKey: String
) {
}
