package com.clone.commerce.product.model.request

import com.clone.commerce.user.enums.UserType

class CategoryDeleteRequestModel(
    val categoryIdx: Long,
    val userIdx: Long,
    val userType: UserType
) {
}