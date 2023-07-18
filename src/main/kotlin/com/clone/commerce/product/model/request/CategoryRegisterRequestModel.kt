package com.clone.commerce.product.model.request

import com.clone.commerce.user.enums.UserType

class CategoryRegisterRequestModel(
    val parentCategoryId: Long? = null,
    val categoryName: String,
    val userType: UserType,
    val userIdx: Long
) {
}
