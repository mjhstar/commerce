package com.clone.commerce.user.model.request

class RefreshTokenRequestModel(
    val email: String,
    val password: String,
    val clientKey: String
) {
}
