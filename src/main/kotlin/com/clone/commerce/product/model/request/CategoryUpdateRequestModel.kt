package com.clone.commerce.product.model.request

class CategoryUpdateRequestModel(
    val idx: Long,
    val mainCategory: String,
    val subCategory: String?,
    val detailCategory: String?
) {

}
