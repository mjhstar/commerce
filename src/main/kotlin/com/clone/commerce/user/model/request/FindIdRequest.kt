package com.clone.commerce.user.model.request

class FindIdRequest(
    val name: String,
    private val phoneNumber: String
) {
    fun toModel() = FindIdRequestModel(
        name = this.name,
        phoneNumber = this.phoneNumber
    )
}
