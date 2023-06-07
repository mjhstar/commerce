package com.clone.commerce.user.model.request

class FindPwRequest(
    val email: String,
    val name: String,
    private val phoneNumber: String
) {
    fun toModel() = FindPwRequestModel(
        email = this.email,
        name = this.name,
        phoneNumber = this.phoneNumber
    )

    fun getPH(): String {
        return this.phoneNumber.replace("-", "").trim()
    }
}
