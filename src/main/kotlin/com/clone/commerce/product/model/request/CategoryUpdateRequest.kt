package com.clone.commerce.product.model.request

class CategoryUpdateRequest(
    val idx: Long,
    val mainCategory: String,
    val subCategory: String?,
    val detailCategory: String?
) {
    fun toModel() = CategoryUpdateRequestModel(
        idx = this.idx,
        mainCategory = this.mainCategory,
        subCategory = this.subCategory,
        detailCategory = this.detailCategory
    )
}
