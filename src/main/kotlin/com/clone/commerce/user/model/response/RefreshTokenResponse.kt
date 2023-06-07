package com.clone.commerce.user.model.response

class RefreshTokenResponse(
    val refreshToken: String,
    val email: String,
    val userIdx: Long
) {
    companion object {
        fun createBy(model: RefreshTokenResponseModel) = RefreshTokenResponse(
            refreshToken = model.refreshToken,
            email = model.email,
            userIdx = model.userIdx
        )
    }
}
