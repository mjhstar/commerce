package com.clone.commerce.product.model.dto

import com.clone.commerce.product.entity.Category

class CategoryDto(
    val idx: Long,
    val name: String,
    val level: Int,
    val parentCategory: ParentCategory? = null,
    val subCategories: List<CategoryDto> = emptyList(),
    val createdBy: Long,
    val createdAt: Long,
    val updatedAt: Long? = null
) {
    class ParentCategory(
        val idx: Long,
        val name: String,
        val level: Int
    ) {
        companion object {
            fun createBy(category: Category): ParentCategory {
                return ParentCategory(
                    idx = category.idx,
                    name = category.name,
                    level = category.level
                )
            }
        }
    }

    companion object {
        fun createBy(category: Category): CategoryDto {
            return CategoryDto(
                idx = category.idx,
                name = category.name,
                level = category.level,
                parentCategory = category.parentCategory?.let { ParentCategory.createBy(it) },
                subCategories = category.subCategories.map {
                    createBy(it)
                },
                createdBy = category.createdBy,
                createdAt = category.createdAt,
                updatedAt = category.updatedAt
            )
        }
    }
}