package com.clone.commerce.product.repository

import com.clone.commerce.product.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface CategoryRepository : JpaRepository<Category, Long>, QuerydslPredicateExecutor<Category> {
    fun findByIdx(idx: Long): Category?
}
