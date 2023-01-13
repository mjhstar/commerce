package com.clone.commerce.product.model.response

import com.clone.commerce.product.entity.MainCategory

class MainCategoryResponse(
    val itemList: List<MainCategoryItem>
) {
    class MainCategoryItem(
        val idx: Long,
        val name: String
    )

    companion object {
        fun createBy(mainCategories: List<MainCategory>): MainCategoryResponse {
            return MainCategoryResponse(
                itemList = mainCategories.map {
                    MainCategoryItem(
                        idx = it.idx,
                        name = it.name
                    )
                }
            )
        }
    }
}
