package com.clone.coupangclone.user.request

import com.clone.coupangclone.common.extension.aesEncode
import com.clone.coupangclone.common.extension.toJson
import com.clone.coupangclone.common.extension.toModel

class SignUpRequest(
    val email: String,
    private val password: String,
    val name: String,
    private val phoneNumber: String,
    val terms: Terms
) {
    class Terms(
        val require: Boolean,
        val marketing: Boolean,
        val email: Boolean,
        val sms: Boolean,
        val appPush: Boolean,
    )

    fun getPH(): String{
        return this.phoneNumber.replace("-", "")
    }

    fun getPW(): String{
        return this.password.aesEncode()
    }

    fun getTerms(): String {
        val termMap = this.terms.toJson().toModel<Map<String, Boolean>>()
        return termMap.filter { it.value }.keys.toJson()
    }
}
