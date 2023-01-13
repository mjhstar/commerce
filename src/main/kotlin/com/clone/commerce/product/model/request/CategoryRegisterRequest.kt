package com.clone.commerce.product.model.request

class CategoryRegisterRequest(
    val mainCategoryIdx: Long,
    val subCategoryName: String,
    val detailCategoryName: String?
) {
}
