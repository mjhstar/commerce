package com.clone.commerce.user.model.request

class RefreshTokenRequest(
    val email: String,
    val password: String,
    val clientKey: String
) {
    fun toModel() = RefreshTokenRequestModel(
        email = this.email,
        password = this.password,
        clientKey = this.clientKey
    )
}
