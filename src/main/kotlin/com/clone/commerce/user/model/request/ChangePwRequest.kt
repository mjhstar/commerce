package com.clone.commerce.user.model.request

class ChangePwRequest(
    val email: String,
    val name: String,
    private val phoneNumber: String,
    private val key: String,
    private val password: String,
    private val checkPassword: String
) {
    fun toModel() = ChangePwRequestModel(
        email = this.email,
        name = this.name,
        phoneNumber = this.phoneNumber,
        key = this.key,
        password = this.password,
        checkPassword = this.checkPassword
    )
}
