package com.clone.commerce.user.model.request

import com.clone.commerce.common.extension.aesEncode
import com.clone.commerce.common.extension.toJson
import com.clone.commerce.common.extension.toModel
import com.clone.commerce.user.enums.UserType
import com.clone.commerce.user.model.AccessTokenParam
import com.clone.commerce.user.model.RefreshTokenParam

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
    ){
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

    fun toRefreshTokenParam():RefreshTokenParam{
        return RefreshTokenParam(
            email = this.email
        )
    }

    fun toAccessTokenParam(userIdx: Long): AccessTokenParam{
        return AccessTokenParam(
            userIdx = userIdx,
            type = this.type,
            email = this.email
        )
    }
}
