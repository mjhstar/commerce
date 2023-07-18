package com.clone.commerce.product.model.request

import com.clone.commerce.user.enums.UserType

class CategoryRegisterRequest(
    val parentCategoryId: Long? = null,
    val categoryName: String,
) {
    fun toModel(userType: UserType, userIdx: Long) = CategoryRegisterRequestModel(
        parentCategoryId = this.parentCategoryId,
        categoryName = this.categoryName,
        userType = userType,
        userIdx = userIdx

    )
}
