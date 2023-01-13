package com.clone.commerce.product.model.response

class CategoryUpdateResponse(
    val idx: Long,
    val mainCategory: String,
    val subCategory: String?,
    val detailCategory: String?
) {
}
