package com.clone.commerce.product.model.request

import com.clone.commerce.user.enums.UserType

class CategoryRegisterRequest(
    val mainCategoryIdx: Long,
    val subCategoryName: String,
    val detailCategoryName: String?
) {
    fun toModel(userType: UserType, userIdx: Long) = CategoryRegisterRequestModel(
        mainCategoryIdx = this.mainCategoryIdx,
        subCategoryName = this.subCategoryName,
        detailCategoryName = this.detailCategoryName,
        userType = userType,
        userIdx = userIdx

    )
}
