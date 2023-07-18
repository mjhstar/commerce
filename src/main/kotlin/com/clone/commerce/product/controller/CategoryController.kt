package com.clone.commerce.product.controller

import com.clone.commerce.common.extension.*
import com.clone.commerce.common.model.CommonResponse
import com.clone.commerce.product.model.request.CategoryRegisterRequest
import com.clone.commerce.product.model.request.CategorySearchRequest
import com.clone.commerce.product.model.response.AllCategoryResponse
import com.clone.commerce.product.model.response.CategoryRegisterResponse
import com.clone.commerce.product.model.response.CategorySearchResponse
import com.clone.commerce.product.service.CategoryService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/product/{apiVersion}/category")
class CategoryController(
    private val categoryService: CategoryService
) {
    companion object {
        private const val ALL_CATEGORY = "/all"
    }

    @PostMapping("")
    fun registerCategory(
        servlet: HttpServletRequest,
        authentication: Authentication,
        @PathVariable apiVersion: String,
        @RequestBody request: CategoryRegisterRequest
    ): CommonResponse<CategoryRegisterResponse> {
        val requestTime = TimeUtils.currentTimeMillis()
        val userIdx = authentication.getUserIdx()
        val userType = authentication.getType()
        val responseModel = categoryService.registerCategory(request.toModel(userType, userIdx))
        return CommonResponse.success(CategoryRegisterResponse.createBy(responseModel), requestTime)
    }

    @GetMapping("")
    fun findCategory(
        servlet: HttpServletRequest,
        authentication: Authentication,
        @PathVariable apiVersion: String,
        @ModelAttribute request: CategorySearchRequest?
    ): CommonResponse<CategorySearchResponse> {
        val requestTime = TimeUtils.currentTimeMillis()
        val responseModel = categoryService.findCategory(request?.toModel())
        return CommonResponse.success(CategorySearchResponse.createBy(responseModel), requestTime)
    }

    @GetMapping(ALL_CATEGORY)
    fun findAllCategory(
        servlet: HttpServletRequest,
        authentication: Authentication,
        @PathVariable apiVersion: String
    ): CommonResponse<AllCategoryResponse> {
        val requestTime = TimeUtils.currentTimeMillis()
        val responseModel = categoryService.findAllCategory()
        return CommonResponse.success(AllCategoryResponse.createBy(responseModel), requestTime)
    }
}
