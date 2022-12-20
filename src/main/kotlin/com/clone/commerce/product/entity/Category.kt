package com.clone.commerce.product.entity

import com.clone.commerce.common.extension.TimeUtils
import com.clone.commerce.common.extension.isNullOrEmptyOrBlank
import com.clone.commerce.product.enums.CategoryDepth
import com.clone.commerce.product.model.request.CategoryRegisterRequest
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "category")
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val categoryIdx: Long = 0,
    val mainCategory: String,
    val subCategory: String? = null,
    val detailCategory: String? = null,
    @Enumerated(EnumType.STRING)
    val depth: CategoryDepth,
    val createdAt: Long = TimeUtils.currentTimeMillis(),
    val updatedAt: Long? = null,
    val createdBy: String,
    val updatedBy: String?
) {

    companion object {
        fun createBy(
            request: CategoryRegisterRequest,
            userId: String
        ): Category {
            return Category(
                mainCategory = request.mainCategory,
                subCategory = request.subCategory,
                detailCategory = request.detailCategory,
                depth = request.getDepth(),
                createdBy = userId,
                updatedBy = userId
            )
        }
    }
}
