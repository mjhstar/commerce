package com.clone.commerce.user.model.request

import com.clone.commerce.common.support.extension.aesEncode
import com.clone.commerce.common.support.extension.toJson
import com.clone.commerce.common.support.extension.toModel
import com.clone.commerce.user.enums.UserType
import com.clone.commerce.user.model.AccessTokenParam
import com.clone.commerce.user.model.RefreshTokenParam

class SignUpRequestModel(
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
    )

    fun getPH(): String {
        return this.phoneNumber.replace("-", "")
    }

    fun getPW(): String {
        return this.password.aesEncode()
    }

    fun getTerms(): String {
        val termMap = this.terms.toJson().toModel<Map<String, Boolean>>()
        return termMap.filter { it.value }.keys.toJson()
    }

    fun toRefreshTokenParam(key: String): RefreshTokenParam {
        return RefreshTokenParam(
            key = key,
            email = this.email
        )
    }

    fun toAccessTokenParam(userIdx: Long, key: String): AccessTokenParam {
        return AccessTokenParam(
            userIdx = userIdx,
            key = key,
            type = this.type,
            email = this.email
        )
    }
}
