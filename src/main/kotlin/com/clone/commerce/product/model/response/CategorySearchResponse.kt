package com.clone.commerce.product.model.response

import com.clone.commerce.product.model.response.item.CategoryItem

class CategorySearchResponse(
    val items: List<CategoryItem>
) {
    companion object {
        fun createBy(model: CategorySearchResponseModel): CategorySearchResponse {
            return CategorySearchResponse(
                items = model.items
            )
        }
    }
}
