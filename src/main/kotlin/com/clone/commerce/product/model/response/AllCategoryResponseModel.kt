package com.clone.commerce.product.model.response

import com.clone.commerce.product.model.dto.CategoryDto
import com.clone.commerce.product.model.response.item.CategoryItem

class AllCategoryResponseModel(
    val items: List<CategoryItem>
) {
    companion object {
        fun createBy(mainCategories: List<CategoryDto>): AllCategoryResponseModel {
            return AllCategoryResponseModel(
                items = mainCategories.map {
                    CategoryItem.createBy(it)
                }
            )
        }
    }
}
