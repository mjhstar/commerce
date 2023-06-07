package com.clone.commerce.user.model.response

class LoginResponse(
    val email: String,
    val userName: String,
    val refreshToken: String,
    val accessToken: String
) {
    companion object {
        fun createBy(model: LoginResponseModel) = LoginResponse(
            email = model.email,
            userName = model.userName,
            refreshToken = model.refreshToken,
            accessToken = model.accessToken
        )
    }
}
