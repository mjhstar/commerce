package com.clone.commerce.product.service

import com.clone.commerce.common.extension.isNullOrEmptyOrBlank
import com.clone.commerce.common.web.exception.BusinessException
import com.clone.commerce.common.web.exception.ErrorCode
import com.clone.commerce.product.entity.Category
import com.clone.commerce.product.model.request.CategoryRegisterRequest
import com.clone.commerce.product.model.response.CategoryRegisterResponse
import com.clone.commerce.product.repository.CategoryRepository
import com.clone.commerce.product.repository.create
import com.clone.commerce.product.repository.validCheck
import com.clone.commerce.user.enums.UserType
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {
    fun registerCategory(
        request: CategoryRegisterRequest, userId: String, userType: UserType
    ): CategoryRegisterResponse {
        if (userType != UserType.ADMIN) {
            throw BusinessException(ErrorCode.INVALID_REQUEST)
        }
        if (request.mainCategory.isNullOrEmptyOrBlank()) {
            throw BusinessException(ErrorCode.INVALID_REQUEST)
        }
        val categoryDepth = request.getDepth()
        val validCheck = categoryRepository.validCheck(
            main = request.mainCategory,
            sub = request.subCategory,
            detail = request.detailCategory,
            depth = categoryDepth
        )
        val category = if (validCheck) {
            categoryRepository.create(Category.createBy(request, userId))
        } else {
            throw BusinessException(ErrorCode.INVALID_CATEGORY)
        }
        return CategoryRegisterResponse(
            categoryIdx = category.categoryIdx,
            mainCategory = category.mainCategory,
            subCategory = category.subCategory,
            detailCategory = category.detailCategory
        )
    }
}
