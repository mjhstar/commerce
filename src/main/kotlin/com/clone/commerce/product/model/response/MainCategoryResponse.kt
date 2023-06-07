package com.clone.commerce.product.model.response

class MainCategoryResponse(
    val itemList: List<MainCategoryItem>
) {
    class MainCategoryItem(
        val idx: Long,
        val name: String
    )

    companion object {
        fun createBy(model: MainCategoryResponseModel): MainCategoryResponse {
            return MainCategoryResponse(
                itemList = model.itemList.map { main ->
                    MainCategoryItem(
                        idx = main.idx,
                        name = main.name
                    )
                }
            )
        }
    }
}
