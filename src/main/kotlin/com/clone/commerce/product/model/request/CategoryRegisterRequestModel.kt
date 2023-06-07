package com.clone.commerce.product.model.request

import com.clone.commerce.user.enums.UserType

class CategoryRegisterRequestModel(
    val mainCategoryIdx: Long,
    val subCategoryName: String,
    val detailCategoryName: String?,
    val userType: UserType,
    val userId: String
) {
}
