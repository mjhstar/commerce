package com.clone.commerce.product.repository

import com.clone.commerce.product.entity.MainCategory
import org.springframework.data.jpa.repository.JpaRepository

interface MainCategoryRepository: JpaRepository<MainCategory, Long> {
    fun existsByName(name: String): Boolean
    fun findByIdx(idx: Long): MainCategory?
}
