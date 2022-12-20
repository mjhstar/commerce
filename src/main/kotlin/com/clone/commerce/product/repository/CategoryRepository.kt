package com.clone.commerce.product.repository

import com.clone.commerce.product.entity.Category
import com.clone.commerce.product.enums.CategoryDepth
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {
    fun existsByMainCategory(main: String): Boolean
    fun existsByMainCategoryAndSubCategory(main: String, sub: String): Boolean
    fun existsByMainCategoryAndSubCategoryAndDetailCategory(main: String, sub: String, detail: String): Boolean
}

fun CategoryRepository.create(category: Category): Category {
    return this.save(category)
}

fun CategoryRepository.validCheck(main: String, sub: String?, detail: String?, depth: CategoryDepth): Boolean {
    return when (depth) {
        CategoryDepth.MAIN -> {
            !existsByMainCategory(main)
        }
        CategoryDepth.SUB -> {
            existsByMainCategory(main) && !existsByMainCategoryAndSubCategory(main, sub!!)
        }
        else -> {
            (existsByMainCategoryAndSubCategory(main, sub!!)
                && !existsByMainCategoryAndSubCategoryAndDetailCategory(main, sub, detail!!))
        }
    }
}
