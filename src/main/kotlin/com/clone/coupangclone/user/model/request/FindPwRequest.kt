package com.clone.coupangclone.user.model.request

class FindPwRequest(
    val email: String,
    val name: String,
    private val phoneNumber: String
) {
    fun getPH():String{
        return this.phoneNumber.replace("-","").trim()
    }
}
