package com.clone.commerce.product.model.response

import com.clone.commerce.product.entity.DetailCategory
import com.clone.commerce.product.entity.MainCategory
import com.clone.commerce.product.entity.SubCategory

class CategoryRegisterResponse(
    val mainCategoryIdx: Long,
    val mainCategoryName: String,
    val subCategoryIdx: Long,
    val subCategoryName: String,
    val detailCategoryIdx: Long?,
    val detailCategoryName: String?
) {
    companion object {
        fun createBy(
            mainCategory: MainCategory,
            subCategory: SubCategory,
            detailCategory: DetailCategory?
        ): CategoryRegisterResponse {
            return CategoryRegisterResponse(
                mainCategoryIdx = mainCategory.idx,
                mainCategoryName = mainCategory.name,
                subCategoryIdx = subCategory.idx,
                subCategoryName = subCategory.name,
                detailCategoryIdx = detailCategory?.idx,
                detailCategoryName = detailCategory?.name
            )
        }
    }
}
