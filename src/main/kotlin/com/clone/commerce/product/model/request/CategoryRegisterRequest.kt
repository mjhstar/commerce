package com.clone.commerce.product.model.request

import com.clone.commerce.common.extension.isNullOrEmptyOrBlank
import com.clone.commerce.product.enums.CategoryDepth

class CategoryRegisterRequest(
    val mainCategory: String,
    var subCategory: String?,
    var detailCategory: String?
) {
    fun getDepth(): CategoryDepth {
        return if (this.detailCategory.isNullOrEmptyOrBlank() && this.subCategory.isNullOrEmptyOrBlank()) {
            this.subCategory = null
            this.detailCategory = null
            CategoryDepth.MAIN
        } else if (this.detailCategory.isNullOrEmptyOrBlank()) {
            this.detailCategory = null
            CategoryDepth.SUB
        } else {
            CategoryDepth.DETAIL
        }
    }
}
