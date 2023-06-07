package com.clone.commerce.product.model.response

import com.clone.commerce.product.entity.DetailCategory
import com.clone.commerce.product.entity.MainCategory
import com.clone.commerce.product.entity.SubCategory

class CategoryRegisterResponseModel(
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
        ): CategoryRegisterResponseModel {
            return CategoryRegisterResponseModel(
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
