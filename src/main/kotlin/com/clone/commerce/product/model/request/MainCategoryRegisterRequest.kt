package com.clone.commerce.product.model.request

import com.clone.commerce.user.enums.UserType

class MainCategoryRegisterRequest(
    val categoryName: String
) {
    fun toModel(userType: UserType, userId: String) = MainCategoryRegisterRequestModel(
        categoryName = this.categoryName,
        userType = userType,
        userId = userId
    )
}
