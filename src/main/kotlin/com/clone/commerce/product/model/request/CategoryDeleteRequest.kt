package com.clone.commerce.product.model.request

import com.clone.commerce.user.enums.UserType

class CategoryDeleteRequest(
    val categoryIdx: Long,
) {
    fun toModel(userIdx: Long, userType: UserType): CategoryDeleteRequestModel{
        return CategoryDeleteRequestModel(
            categoryIdx = this.categoryIdx,
            userIdx = userIdx,
            userType = userType
        )
    }
}