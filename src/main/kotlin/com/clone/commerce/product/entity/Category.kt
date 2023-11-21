package com.clone.commerce.product.entity

import com.clone.commerce.common.support.extension.TimeUtils
import com.clone.commerce.product.model.dto.CategoryDto
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity(name = "category")
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long = 0,
    @Column(unique = true)
    val name: String,
    val level: Int,


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentCategoryIdx")
    val parentCategory: Category? = null,

    @JsonIgnore
    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
    val subCategories: List<Category> = emptyList(),
    val createdBy: Long,
    val createdAt: Long = TimeUtils.currentTimeMillis(),
    val updatedAt: Long? = null
) {
    fun toDto(): CategoryDto {
        return CategoryDto.createBy(this)
    }
}