package com.clone.coupangclone.user.model.request

class RefreshTokenRequest(
    val email: String,
    val password: String,
    val clientKey: String
) {
}
