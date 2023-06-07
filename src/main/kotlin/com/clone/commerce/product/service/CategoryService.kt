package com.clone.commerce.product.service

import com.clone.commerce.common.web.exception.BusinessException
import com.clone.commerce.common.web.exception.ErrorCode
import com.clone.commerce.product.entity.DetailCategory
import com.clone.commerce.product.entity.MainCategory
import com.clone.commerce.product.entity.SubCategory
import com.clone.commerce.product.model.request.CategoryRegisterRequestModel
import com.clone.commerce.product.model.request.MainCategoryRegisterRequestModel
import com.clone.commerce.product.model.response.AllCategoryResponseModel
import com.clone.commerce.product.model.response.CategoryRegisterResponseModel
import com.clone.commerce.product.model.response.MainCategoryRegisterResponseModel
import com.clone.commerce.product.model.response.MainCategoryResponseModel
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
        request: MainCategoryRegisterRequestModel
    ): MainCategoryRegisterResponseModel {
        if (request.userType != UserType.ADMIN) {
            throw BusinessException(ErrorCode.INVALID_REQUEST)
        }
        val mainCategory = mainCategoryRepository.save(
            MainCategory(
                name = request.categoryName,
                createdBy = request.userId
            )
        )
        return MainCategoryRegisterResponseModel(
            idx = mainCategory.idx,
            name = mainCategory.name
        )
    }

    fun registerCategory(
        request: CategoryRegisterRequestModel
    ): CategoryRegisterResponseModel {
        if (request.userType != UserType.ADMIN) {
            throw BusinessException(ErrorCode.INVALID_REQUEST)
        }
        val mainCategory = mainCategoryRepository.findByIdx(request.mainCategoryIdx)
            ?: throw BusinessException(ErrorCode.NOT_EXIST_CATEGORY)
        val subCategory = subCategoryRepository.findByName(request.subCategoryName) ?: subCategoryRepository.save(
            SubCategory(
                name = request.subCategoryName,
                mainCategory = mainCategory,
                createdBy = request.userId
            )
        )
        val detailCategory = request.detailCategoryName?.let {
            detailCategoryRepository.save(
                DetailCategory(
                    name = it,
                    subCategory = subCategory,
                    createdBy = request.userId
                )
            )
        }
        return CategoryRegisterResponseModel.createBy(mainCategory, subCategory, detailCategory)
    }

    fun findAllCategory(): AllCategoryResponseModel {
        val mainCategoryList = mainCategoryRepository.findAll()
        return AllCategoryResponseModel.createBy(mainCategoryList)
    }

    fun findMainCategory(): MainCategoryResponseModel {
        val mainCategoryList = mainCategoryRepository.findAll()
        return MainCategoryResponseModel.createBy(mainCategoryList)
    }
}
