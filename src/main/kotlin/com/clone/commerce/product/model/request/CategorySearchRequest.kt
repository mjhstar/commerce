package com.clone.commerce.product.model.request

class CategorySearchRequest(
    val name: String?,
    val level: Int?,
){
    fun toModel() : CategorySearchRequestModel{
        return CategorySearchRequestModel(
            name = this.name,
            level = this.level
        )
    }
}