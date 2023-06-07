package com.clone.commerce.user.model.response

class SignUpResponse(
    val accessToken: String,
    val refreshToken: String,
    val userIdx: Long
) {
    companion object {
        fun createBy(model: SignUpResponseModel) = SignUpResponse(
            accessToken = model.accessToken,
            refreshToken = model.refreshToken,
            userIdx = model.userIdx
        )
    }
}
