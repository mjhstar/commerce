package com.clone.commerce.product.model.response

import com.clone.commerce.product.model.dto.CategoryDto
import com.clone.commerce.product.model.response.item.CategoryItem

class CategoryRegisterResponseModel(
    val category: CategoryItem
) {

    companion object {
        fun createBy(category: CategoryDto): CategoryRegisterResponseModel {
            return CategoryRegisterResponseModel(CategoryItem.createBy(category))
        }
    }
}
