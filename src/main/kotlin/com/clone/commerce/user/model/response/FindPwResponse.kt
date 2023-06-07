package com.clone.commerce.user.model.response

class FindPwResponse(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val key: String
) {
    companion object {
        fun createBy(model: FindPwResponseModel) = FindPwResponse(
            name = model.name,
            email = model.email,
            phoneNumber = model.phoneNumber,
            key = model.key
        )
    }
}
