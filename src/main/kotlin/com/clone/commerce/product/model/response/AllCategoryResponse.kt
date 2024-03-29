package com.clone.commerce.product.model.response

import com.clone.commerce.product.model.response.item.CategoryItem

class AllCategoryResponse(
    val items: List<CategoryItem>
) {
    companion object {
        fun createBy(model: AllCategoryResponseModel): AllCategoryResponse {
            return AllCategoryResponse(
                items = model.items
            )
        }
    }
}
