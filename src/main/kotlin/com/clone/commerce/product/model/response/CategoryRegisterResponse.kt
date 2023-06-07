package com.clone.commerce.product.model.response

class CategoryRegisterResponse(
    val mainCategoryIdx: Long,
    val mainCategoryName: String,
    val subCategoryIdx: Long,
    val subCategoryName: String,
    val detailCategoryIdx: Long?,
    val detailCategoryName: String?
) {
    companion object {
        fun createBy(model: CategoryRegisterResponseModel): CategoryRegisterResponse {
            return CategoryRegisterResponse(
                mainCategoryIdx = model.mainCategoryIdx,
                mainCategoryName = model.mainCategoryName,
                subCategoryIdx = model.subCategoryIdx,
                subCategoryName = model.subCategoryName,
                detailCategoryIdx = model.detailCategoryIdx,
                detailCategoryName = model.detailCategoryName
            )
        }
    }
}
