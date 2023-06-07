package com.clone.commerce.product.model.response

import com.clone.commerce.product.entity.MainCategory

class AllCategoryResponseModel(
    val mainCategory: List<MainCategoryItem>
) {
    class MainCategoryItem(
        val idx: Long,
        val name: String,
        val subCategory: List<SubCategoryItem>
    )

    class SubCategoryItem(
        val idx: Long,
        val name: String,
        val detailCategory: List<DetailCategoryItem>
    )

    class DetailCategoryItem(
        val idx: Long,
        val name: String
    )

    companion object {
        fun createBy(mainCategoryList: List<MainCategory>): AllCategoryResponseModel {
            return AllCategoryResponseModel(
                mainCategory = mainCategoryList.map { main ->
                    MainCategoryItem(
                        idx = main.idx,
                        name = main.name,
                        subCategory = main.subCategory.map { sub ->
                            SubCategoryItem(
                                idx = sub.idx,
                                name = sub.name,
                                detailCategory = sub.detailCategory.map { detail ->
                                    DetailCategoryItem(
                                        idx = detail.idx,
                                        name = detail.name
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    }
}
