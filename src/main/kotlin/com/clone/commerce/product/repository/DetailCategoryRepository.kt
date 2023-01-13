package com.clone.commerce.product.repository

import com.clone.commerce.product.entity.DetailCategory
import org.springframework.data.jpa.repository.JpaRepository

interface DetailCategoryRepository: JpaRepository<DetailCategory, Long> {
}
