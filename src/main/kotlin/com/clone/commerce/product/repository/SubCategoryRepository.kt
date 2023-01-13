package com.clone.commerce.product.repository

import com.clone.commerce.product.entity.SubCategory
import org.springframework.data.jpa.repository.JpaRepository

interface SubCategoryRepository : JpaRepository<SubCategory, Long> {
    fun findByName(name: String): SubCategory?
}
