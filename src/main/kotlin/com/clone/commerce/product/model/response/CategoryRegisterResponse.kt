package com.clone.commerce.product.model.response

import com.clone.commerce.product.model.response.item.CategoryItem

class CategoryRegisterResponse(
    val category: CategoryItem
) {
    companion object {
        fun createBy(model: CategoryRegisterResponseModel): CategoryRegisterResponse {
            return CategoryRegisterResponse(category = model.category)
        }
    }
}
