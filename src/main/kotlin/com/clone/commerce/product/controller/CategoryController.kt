package com.clone.commerce.product.controller

import com.clone.commerce.common.extension.getEmail
import com.clone.commerce.common.extension.getId
import com.clone.commerce.common.extension.getType
import com.clone.commerce.common.model.CommonResponse
import com.clone.commerce.product.model.request.CategoryRegisterRequest
import com.clone.commerce.product.model.request.MainCategoryRegisterRequest
import com.clone.commerce.product.model.response.AllCategoryResponse
import com.clone.commerce.product.model.response.CategoryRegisterResponse
import com.clone.commerce.product.model.response.MainCategoryRegisterResponse
import com.clone.commerce.product.model.response.MainCategoryResponse
import com.clone.commerce.product.service.CategoryService
import javax.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product/category")
class CategoryController(
    private val categoryService: CategoryService
) {
    companion object {
        private const val REGISTER_CATEGORY = "/v1/register"
        private const val REGISTER_MAIN_CATEGORY = "/v1/register/main"
        private const val ALL_CATEGORY = "/v1/all"
        private const val MAIN_CATEGORY = "/v1/main"
        private const val UPDATE_CATEGORY = "/v1/update"
        private const val FIND_CATEGORY = "/v1/list"
    }

    @PostMapping(REGISTER_MAIN_CATEGORY)
    fun registerMainCategory(
        servlet: HttpServletRequest, authentication: Authentication, @RequestBody request: MainCategoryRegisterRequest
    ): CommonResponse<MainCategoryRegisterResponse> {
        val userId = authentication.getEmail().getId()
        val userType = authentication.getType()
        val responseModel = categoryService.registerMainCategory(request.toModel(userType, userId))
        return CommonResponse.success(MainCategoryRegisterResponse.createBy(responseModel))
    }

    @PostMapping(REGISTER_CATEGORY)
    fun registerCategory(
        servlet: HttpServletRequest, authentication: Authentication, @RequestBody request: CategoryRegisterRequest
    ): CommonResponse<CategoryRegisterResponse> {
        val userId = authentication.getEmail().getId()
        val userType = authentication.getType()
        val responseModel = categoryService.registerCategory(request.toModel(userType, userId))
        return CommonResponse.success(CategoryRegisterResponse.createBy(responseModel))
    }

    @GetMapping(ALL_CATEGORY)
    fun findAllCategory(
        servlet: HttpServletRequest, authentication: Authentication
    ): CommonResponse<AllCategoryResponse> {
        val responseModel = categoryService.findAllCategory()
        return CommonResponse.success(AllCategoryResponse.createBy(responseModel))
    }

    @GetMapping(MAIN_CATEGORY)
    fun findMainCategory(
        servlet: HttpServletRequest, authentication: Authentication
    ): CommonResponse<MainCategoryResponse> {
        val responseModel = categoryService.findMainCategory()
        return CommonResponse.success(MainCategoryResponse.createBy(responseModel))
    }
}
