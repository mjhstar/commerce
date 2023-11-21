package com.clone.commerce.point.controller

import com.clone.commerce.common.model.CommonResponse
import com.clone.commerce.common.support.extension.TimeUtils
import com.clone.commerce.common.support.extension.getUserIdx
import com.clone.commerce.common.web.exception.BusinessException
import com.clone.commerce.common.web.exception.ErrorCode
import com.clone.commerce.point.model.dto.PointTxResponseDto
import com.clone.commerce.point.model.request.PointChargeRequest
import com.clone.commerce.point.model.request.PointGetHistoriesRequest
import com.clone.commerce.point.model.request.PointUseCancelRequest
import com.clone.commerce.point.model.request.PointUseRequest
import com.clone.commerce.point.model.response.PointChargeResponse
import com.clone.commerce.point.model.response.PointGetResponse
import com.clone.commerce.point.model.response.PointUseCancelResponse
import com.clone.commerce.point.model.response.PointUseResponse
import com.clone.commerce.point.service.PointService
import org.springframework.data.domain.Page
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/point/{apiVersion}")
class PointController(
    private val pointService: PointService
) {
    @GetMapping("")
    fun getPoint(
        servlet: HttpServletRequest,
        authentication: Authentication?,
        @PathVariable apiVersion: String,
    ): CommonResponse<PointGetResponse> {
        val requestTime = TimeUtils.currentTimeMillis()
        val userIdx = authentication?.getUserIdx() ?: throw BusinessException(ErrorCode.NOT_EXIST_USER)
        val responseModel = pointService.getPoint(userIdx)
        return CommonResponse.success(
            data = PointGetResponse.createBy(responseModel),
            requestTime = requestTime
        )
    }

    @PostMapping("/charge")
    fun savePoint(
        servlet: HttpServletRequest,
        authentication: Authentication?,
        @PathVariable apiVersion: String,
        @RequestBody request: PointChargeRequest
    ): CommonResponse<PointChargeResponse> {
        val requestTime = TimeUtils.currentTimeMillis()
        val userIdx = authentication?.getUserIdx() ?: throw BusinessException(ErrorCode.NOT_EXIST_USER)
        val responseModel = pointService.chargePoint(request.toModel(userIdx))
        return CommonResponse.success(
            data = PointChargeResponse.createBy(responseModel),
            requestTime = requestTime
        )
    }

    @PostMapping("/use")
    fun usePoint(
        servlet: HttpServletRequest,
        authentication: Authentication?,
        @PathVariable apiVersion: String,
        @RequestBody request: PointUseRequest
    ): CommonResponse<PointUseResponse> {
        val requestTime = TimeUtils.currentTimeMillis()
        val userIdx = authentication?.getUserIdx() ?: throw BusinessException(ErrorCode.NOT_EXIST_USER)
        val responseModel = pointService.usePoint(request.toModel(userIdx))
        return CommonResponse.success(
            data = PointUseResponse.createBy(responseModel),
            requestTime = requestTime
        )
    }

    @PutMapping("/cancel")
    fun cancelUsePoint(
        servlet: HttpServletRequest,
        authentication: Authentication?,
        @PathVariable apiVersion: String,
        @RequestBody request: PointUseCancelRequest
    ): CommonResponse<PointUseCancelResponse> {
        val requestTime = TimeUtils.currentTimeMillis()
        val userIdx = authentication?.getUserIdx() ?: throw BusinessException(ErrorCode.NOT_EXIST_USER)
        val responseModel = pointService.cancelUsePoint(request.toModel(userIdx))
        return CommonResponse.success(
            data = PointUseCancelResponse.createBy(responseModel),
            requestTime = requestTime
        )

    }

    @GetMapping("/history")
    fun getPointHistories(
        servlet: HttpServletRequest,
        authentication: Authentication?,
        @PathVariable apiVersion: String,
        @ModelAttribute condition: PointGetHistoriesRequest
    ): CommonResponse<Page<PointTxResponseDto>> {
        val requestTime = TimeUtils.currentTimeMillis()
        val userIdx = authentication?.getUserIdx() ?: throw BusinessException(ErrorCode.NOT_EXIST_USER)
        val response = pointService.getPointHistories(condition.toModel(userIdx))
        return CommonResponse.success(
            data = response,
            requestTime = requestTime
        )
    }
}