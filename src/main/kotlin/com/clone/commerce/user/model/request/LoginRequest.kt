package com.clone.commerce.user.model.request

class LoginRequest(
    val email: String,
    val refreshToken: String,
    val clientKey: String
) {
}
