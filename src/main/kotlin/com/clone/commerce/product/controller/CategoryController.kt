package com.clone.commerce.product.controller

import com.clone.commerce.common.extension.getEmail
import com.clone.commerce.common.extension.getId
import com.clone.commerce.common.extension.getType
import com.clone.commerce.common.extension.getUserIdx
import com.clone.commerce.common.model.CommonResponse
import com.clone.commerce.product.model.request.CategoryRegisterRequest
import com.clone.commerce.product.model.request.MainCategoryRegisterRequest
import com.clone.commerce.product.model.response.AllCategoryResponse
import com.clone.commerce.product.model.response.CategoryRegisterResponse
import com.clone.commerce.product.model.response.MainCategoryRegisterResponse
import com.clone.commerce.product.model.response.MainCategoryResponse
import com.clone.commerce.product.service.CategoryService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.time.OffsetDateTime
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/product/category/{apiVersion}")
class CategoryController(
    private val categoryService: CategoryService
) {
    companion object {
        private const val REGISTER_CATEGORY = "/register"
        private const val REGISTER_MAIN_CATEGORY = "/register/main"
        private const val ALL_CATEGORY = "/all"
        private const val MAIN_CATEGORY = "/main"
        private const val UPDATE_CATEGORY = "/update"
        private const val FIND_CATEGORY = "/list"
    }

    @PostMapping(REGISTER_MAIN_CATEGORY)
    fun registerMainCategory(
        servlet: HttpServletRequest,
        authentication: Authentication,
        @RequestBody request: MainCategoryRegisterRequest
    ): CommonResponse<MainCategoryRegisterResponse> {
        val requestTime = OffsetDateTime.now()
        val userIdx = authentication.getUserIdx()
        val userType = authentication.getType()
        val responseModel = categoryService.registerMainCategory(request.toModel(userType, userIdx))
        return CommonResponse.success(MainCategoryRegisterResponse.createBy(responseModel), requestTime)
    }

    @PostMapping(REGISTER_CATEGORY)
    fun registerCategory(
        servlet: HttpServletRequest,
        authentication: Authentication,
        @RequestBody request: CategoryRegisterRequest
    ): CommonResponse<CategoryRegisterResponse> {
        val requestTime = OffsetDateTime.now()
        val userIdx = authentication.getUserIdx()
        val userType = authentication.getType()
        val responseModel = categoryService.registerCategory(request.toModel(userType, userIdx))
        return CommonResponse.success(CategoryRegisterResponse.createBy(responseModel), requestTime)
    }

    @GetMapping(ALL_CATEGORY)
    fun findAllCategory(
        servlet: HttpServletRequest,
        authentication: Authentication
    ): CommonResponse<AllCategoryResponse> {
        val requestTime = OffsetDateTime.now()
        val responseModel = categoryService.findAllCategory()
        return CommonResponse.success(AllCategoryResponse.createBy(responseModel), requestTime)
    }

    @GetMapping(MAIN_CATEGORY)
    fun findMainCategory(
        servlet: HttpServletRequest,
        authentication: Authentication
    ): CommonResponse<MainCategoryResponse> {
        val requestTime = OffsetDateTime.now()
        val responseModel = categoryService.findMainCategory()
        return CommonResponse.success(MainCategoryResponse.createBy(responseModel), requestTime)
    }
}
