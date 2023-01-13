package com.clone.commerce.product.service

import com.clone.commerce.common.web.exception.BusinessException
import com.clone.commerce.common.web.exception.ErrorCode
import com.clone.commerce.product.entity.DetailCategory
import com.clone.commerce.product.entity.MainCategory
import com.clone.commerce.product.entity.SubCategory
import com.clone.commerce.product.model.request.CategoryRegisterRequest
import com.clone.commerce.product.model.request.MainCategoryRegisterRequest
import com.clone.commerce.product.model.response.AllCategoryResponse
import com.clone.commerce.product.model.response.CategoryRegisterResponse
import com.clone.commerce.product.model.response.MainCategoryRegisterResponse
import com.clone.commerce.product.model.response.MainCategoryResponse
import com.clone.commerce.product.repository.DetailCategoryRepository
import com.clone.commerce.product.repository.MainCategoryRepository
import com.clone.commerce.product.repository.SubCategoryRepository
import com.clone.commerce.user.enums.UserType
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val mainCategoryRepository: MainCategoryRepository,
    private val subCategoryRepository: SubCategoryRepository,
    private val detailCategoryRepository: DetailCategoryRepository
) {
    fun registerMainCategory(
        request: MainCategoryRegisterRequest,
        userType: UserType,
        userId: String
    ): MainCategoryRegisterResponse {
        if (userType != UserType.ADMIN) {
            throw BusinessException(ErrorCode.INVALID_REQUEST)
        }
        val mainCategory = mainCategoryRepository.save(
            MainCategory(
                name = request.categoryName,
                createdBy = userId
            )
        )
        return MainCategoryRegisterResponse(
            idx = mainCategory.idx,
            name = mainCategory.name
        )
    }

    fun registerCategory(
        request: CategoryRegisterRequest, userId: String, userType: UserType
    ): CategoryRegisterResponse {
        if (userType != UserType.ADMIN) {
            throw BusinessException(ErrorCode.INVALID_REQUEST)
        }
        val mainCategory = mainCategoryRepository.findByIdx(request.mainCategoryIdx)
            ?: throw BusinessException(ErrorCode.NOT_EXIST_CATEGORY)
        val subCategory = subCategoryRepository.findByName(request.subCategoryName) ?: subCategoryRepository.save(
            SubCategory(
                name = request.subCategoryName,
                mainCategory = mainCategory,
                createdBy = userId
            )
        )
        val detailCategory = request.detailCategoryName?.let {
            detailCategoryRepository.save(
                DetailCategory(
                    name = it,
                    subCategory = subCategory,
                    createdBy = userId
                )
            )
        }
        return CategoryRegisterResponse.createBy(mainCategory, subCategory, detailCategory)
    }

    fun findAllCategory(): AllCategoryResponse {
        val mainCategoryList = mainCategoryRepository.findAll()
        return AllCategoryResponse.createBy(mainCategoryList)
    }

    fun findMainCategory(): MainCategoryResponse {
        val mainCategoryList = mainCategoryRepository.findAll()
        return MainCategoryResponse.createBy(mainCategoryList)
    }

}
