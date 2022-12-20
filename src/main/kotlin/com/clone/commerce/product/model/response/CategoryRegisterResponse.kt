package com.clone.commerce.product.model.response

class CategoryRegisterResponse(
    val categoryIdx: Long,
    val mainCategory: String,
    val subCategory: String?,
    val detailCategory: String?
) {
}
