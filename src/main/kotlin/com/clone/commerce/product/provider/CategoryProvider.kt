package com.clone.commerce.product.provider

import com.clone.commerce.common.support.extension.isTrueThen
import com.clone.commerce.common.web.exception.BusinessException
import com.clone.commerce.common.web.exception.ErrorCode
import com.clone.commerce.product.entity.Category
import com.clone.commerce.product.entity.QCategory
import com.clone.commerce.product.model.dto.CategoryDto
import com.clone.commerce.product.model.request.CategoryRegisterRequestModel
import com.clone.commerce.product.model.request.CategorySearchRequestModel
import com.clone.commerce.product.repository.CategoryRepository
import com.querydsl.core.BooleanBuilder
import org.springframework.stereotype.Component

@Component
class CategoryProvider(
    private val categoryRepository: CategoryRepository
) {
    fun createCategory(request: CategoryRegisterRequestModel): CategoryDto {
        try {
            val category = request.parentCategoryId?.let {
                categoryRepository.findByIdx(it)
            }?.let {
                val subCategory = Category(
                    name = request.categoryName,
                    parentCategory = it,
                    level = it.level + 1,
                    createdBy = request.userIdx
                )
                categoryRepository.save(subCategory)
            } ?: run {
                val category = Category(
                    name = request.categoryName,
                    level = 1,
                    createdBy = request.userIdx
                )
                categoryRepository.save(category)
            }
            return category.toDto()
        } catch (e: Exception) {
            throw BusinessException(ErrorCode.FAIL_REGISTER_CATEGORY)
        }
    }

    fun findCategoryList(request: CategorySearchRequestModel?): List<CategoryDto> {
        val builder = BooleanBuilder()
        request?.name?.let { builder.and(QCategory.category.name.like(it)) }
        request?.level?.let { builder.and(QCategory.category.level.eq(it)) }
        return categoryRepository.findAll(builder).toList().map { it.toDto() }
    }

    fun deleteCategory(categoryIndex: Long): Boolean {
        val category = categoryRepository.findByIdx(categoryIndex)
            ?: throw BusinessException(ErrorCode.NOT_EXIST_CATEGORY)

        category.subCategories.isNotEmpty().isTrueThen {
            throw BusinessException(ErrorCode.FAIL_DELETE_CATEGORY)
        }


        return try {
            categoryRepository.delete(category)
            true
        } catch (e: Exception) {
            false
        }

    }
}