package com.clone.commerce.user.model.response

class FindIdResponse(
    val email: String
) {
    companion object {
        fun createBy(model: FindIdResponseModel) = FindIdResponse(
            email = model.email
        )
    }
}
