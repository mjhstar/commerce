package com.clone.commerce.user.model.response

class LoginResponse(
    val email: String,
    val userName: String,
    val userIdx: Long,
    val refreshToken: String,
    val accessToken: String
) {
    companion object {
        fun createBy(model: LoginResponseModel) = LoginResponse(
            email = model.email,
            userName = model.userName,
            userIdx = model.userIdx,
            refreshToken = model.refreshToken,
            accessToken = model.accessToken
        )
    }
}
