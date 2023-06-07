package com.clone.commerce.product.model.request

import com.clone.commerce.user.enums.UserType

class MainCategoryRegisterRequestModel(
    val categoryName: String,
    val userType: UserType,
    val userId: String
) {
}
