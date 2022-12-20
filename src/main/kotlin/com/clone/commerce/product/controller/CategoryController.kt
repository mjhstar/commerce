package com.clone.commerce.product.controller

import com.clone.commerce.common.config.rx.SchedulerFactory
import com.clone.commerce.common.extension.getEmail
import com.clone.commerce.common.extension.getId
import com.clone.commerce.common.extension.getType
import com.clone.commerce.common.model.CommonResponse
import com.clone.commerce.common.util.ResponseUtil
import com.clone.commerce.common.web.exception.BusinessException
import com.clone.commerce.common.web.exception.ErrorCode
import com.clone.commerce.product.model.request.CategoryRegisterRequest
import com.clone.commerce.product.service.CategoryService
import com.clone.commerce.user.enums.UserType
import io.reactivex.rxjava3.core.Observable
import javax.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication
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
        private const val UPDATE_CATEGORY = "/v1/update"
        private const val FIND_CATEGORY = "/v1/list"
    }

    @PostMapping(REGISTER_CATEGORY)
    fun registerCategory(
        servlet: HttpServletRequest,
        authentication: Authentication,
        @RequestBody request: CategoryRegisterRequest
    ): Observable<CommonResponse> {
        val userId = authentication.getEmail().getId()
        val userType = authentication.getType()
        return ResponseUtil.result(
            response = Observable.fromCallable { categoryService.registerCategory(request, userId, userType) },
            scheduler = SchedulerFactory.GLOBAL.scheduler,
            request = request,
            servlet = servlet
        )
    }
}
