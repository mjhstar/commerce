package com.clone.commerce.product.model.response

class MainCategoryRegisterResponse(
    val idx: Long,
    val name: String
) {
    companion object {
        fun createBy(model: MainCategoryRegisterResponseModel) = MainCategoryRegisterResponse(
            idx = model.idx,
            name = model.name
        )
    }
}
