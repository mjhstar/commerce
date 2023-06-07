package com.clone.commerce.user.model.request

import com.clone.commerce.user.enums.UserType

class SignUpRequest(
    val email: String,
    val type: UserType,
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
    ) {
        fun toModel() = SignUpRequestModel.Terms(
            require = this.require,
            marketing = this.marketing,
            email = this.email,
            sms = this.sms,
            appPush = this.appPush
        )
    }

    fun toModel() = SignUpRequestModel(
        email = this.email,
        type = this.type,
        password = this.password,
        name = this.name,
        phoneNumber = this.phoneNumber,
        terms = this.terms.toModel()
    )
}
