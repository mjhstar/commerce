package com.clone.commerce.product.service

import com.clone.commerce.common.web.exception.BusinessException
import com.clone.commerce.common.web.exception.ErrorCode
import com.clone.commerce.product.model.request.CategoryRegisterRequestModel
import com.clone.commerce.product.model.request.CategorySearchRequestModel
import com.clone.commerce.product.model.response.AllCategoryResponseModel
import com.clone.commerce.product.model.response.CategoryRegisterResponseModel
import com.clone.commerce.product.model.response.CategorySearchResponseModel
import com.clone.commerce.product.provider.CategoryProvider
import com.clone.commerce.user.enums.UserType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService(
    private val categoryProvider: CategoryProvider
) {
    fun registerCategory(
        request: CategoryRegisterRequestModel
    ): CategoryRegisterResponseModel {
        if (request.userType != UserType.ADMIN) {
            throw BusinessException(ErrorCode.INVALID_REQUEST)
        }
        val category = categoryProvider.createCategory(request)
        return CategoryRegisterResponseModel.createBy(category)
    }

    @Transactional
    fun findAllCategory(): AllCategoryResponseModel {
        val categories = categoryProvider.findCategoryList(null)
        return AllCategoryResponseModel.createBy(categories)
    }

    @Transactional
    fun findCategory(request: CategorySearchRequestModel?): CategorySearchResponseModel {
        val categories = categoryProvider.findCategoryList(request)
        return CategorySearchResponseModel.createBy(categories)
    }
}
