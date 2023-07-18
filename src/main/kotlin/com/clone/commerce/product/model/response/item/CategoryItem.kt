package com.clone.commerce.product.model.response.item

import com.clone.commerce.product.model.dto.CategoryDto

class CategoryItem(
    val idx: Long,
    val name: String,
    val level: Int,
    val parentCategory: ParentCategoryItem? = null,
    val subCategories: List<CategoryItem>
) {
    class ParentCategoryItem(
        val idx: Long,
        val name: String,
        val level: Int,
    )

    companion object {
        fun createBy(category: CategoryDto): CategoryItem {
            return CategoryItem(
                idx = category.idx,
                name = category.name,
                level = category.level,
                parentCategory = category.parentCategory?.let {
                    ParentCategoryItem(
                        idx = it.idx,
                        name = it.name,
                        level = it.level
                    )
                },
                subCategories = category.subCategories.map {
                    createBy(it)
                }

            )
        }
    }
}