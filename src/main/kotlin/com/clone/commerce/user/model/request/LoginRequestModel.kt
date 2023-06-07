package com.clone.commerce.user.model.request

class LoginRequestModel(
    val email: String,
    val refreshToken: String,
    val clientKey: String
) {
}
