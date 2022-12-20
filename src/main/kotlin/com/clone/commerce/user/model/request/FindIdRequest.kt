package com.clone.commerce.user.model.request

class FindIdRequest(
    val name: String,
    private val phoneNumber: String
) {
    fun getPH():String{
        return this.phoneNumber.replace("-","")
    }
}
