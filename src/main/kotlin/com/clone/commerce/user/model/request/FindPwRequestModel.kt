package com.clone.commerce.user.model.request

class FindPwRequestModel(
    val email: String,
    val name: String,
    private val phoneNumber: String
) {
    fun getPH():String{
        return this.phoneNumber.replace("-","").trim()
    }
}
