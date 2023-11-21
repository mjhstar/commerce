package com.clone.commerce.point.service

import com.clone.commerce.common.logging.LoggingCompanion
import com.clone.commerce.common.support.extension.isFalseThenThrow
import com.clone.commerce.common.support.extension.isTrueThenThrow
import com.clone.commerce.common.web.exception.BusinessException
import com.clone.commerce.common.web.exception.ErrorCode
import com.clone.commerce.point.entity.PointCharge
import com.clone.commerce.point.enums.PointTxState
import com.clone.commerce.point.model.dto.PointTxResponseDto
import com.clone.commerce.point.model.request.PointChargeRequestModel
import com.clone.commerce.point.model.request.PointGetHistoriesRequestModel
import com.clone.commerce.point.model.request.PointUseCancelRequestModel
import com.clone.commerce.point.model.request.PointUseRequestModel
import com.clone.commerce.point.model.response.PointChargeResponseModel
import com.clone.commerce.point.model.response.PointGetResponseModel
import com.clone.commerce.point.model.response.PointUseCancelResponseModel
import com.clone.commerce.point.model.response.PointUseResponseModel
import com.clone.commerce.point.provider.*
import com.clone.commerce.user.provider.UserProvider
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PointService(
    private val pointProvider: PointProvider,
    private val pointChargeProvider: PointChargeProvider,
    private val pointUseHistoryProvider: PointUseHistoryProvider,
    private val pointTxProvider: PointTxProvider,
    private val userProvider: UserProvider,
    private val userLockProvider: UserLockProvider
) {
    companion object : LoggingCompanion()

    @Transactional(readOnly = true)
    fun getPoint(userIdx: Long): PointGetResponseModel {
        userProvider.isExistUser(userIdx).isFalseThenThrow(ErrorCode.NOT_EXIST_USER)
        val pointDto = pointProvider.getPoint(userIdx)
        return PointGetResponseModel.createBy(pointDto)
    }

    @Transactional
    fun getPointHistories(request: PointGetHistoriesRequestModel): Page<PointTxResponseDto> {
        return pointTxProvider.getHistories(request)
    }

    @Transactional(rollbackFor = [Exception::class])
    fun chargePoint(request: PointChargeRequestModel): PointChargeResponseModel {
        val userLock = userLockProvider.getUserLock(request.userIdx)
        (request.chargePoints <= 0).isTrueThenThrow(ErrorCode.FAIL_CHARGE_POINT_ZERO)
        synchronized(userLock) {
            val user = userProvider.getUserEntity(request.userIdx)
            try {
                val pointDto = pointProvider.chargePointAndGetDto(user, request.chargePoints)
                val pointTx = pointTxProvider.createPointTxAndGetEntity(
                    user = user,
                    points = request.chargePoints,
                    state = PointTxState.CHARGE
                )
                pointChargeProvider.chargePoint(user, request.chargePoints, pointTx)
                return PointChargeResponseModel.createBy(pointTx, pointDto)
            } catch (e: BusinessException) {
                throw e
            } catch (e: Exception) {
                logger.error(e.message)
                throw BusinessException(ErrorCode.FAIL_CHARGE_POINT)
            }
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    fun usePoint(request: PointUseRequestModel): PointUseResponseModel {
        val userLock = userLockProvider.getUserLock(request.userIdx)
        (request.usePoints <= 0).isTrueThenThrow(ErrorCode.FAIL_USE_POINT_ZERO)
        synchronized(userLock) {
            try {
                pointProvider.isAvailableUsePoint(request.userIdx, request.usePoints)
                    .isFalseThenThrow(ErrorCode.IS_MORE_THAN_HAS_POINT)

                val user = userProvider.getUserEntity(request.userIdx)
                val pointTx = pointTxProvider.createPointTxAndGetEntity(
                    user = user,
                    points = request.usePoints,
                    state = PointTxState.USED
                )
                val pointChargeList = pointChargeProvider.getPointChargeEntityList(user.userIdx, request.usePoints)
                pointValidCheck(request.usePoints, pointChargeList).isFalseThenThrow(ErrorCode.FAIL_INVALID_POINT)
                pointUseHistoryProvider.createUseHistories(pointChargeList, request.usePoints, pointTx)
                val point = pointProvider.usePointAndGetEntity(user.userIdx, request.usePoints)

                return PointUseResponseModel(
                    txIdx = pointTx.txIdx,
                    userIdx = user.userIdx,
                    usePoints = request.usePoints,
                    remainPoints = point.remainPoints
                )
            } catch (e: BusinessException) {
                throw e
            } catch (e: Exception) {
                logger.error(e.message)
                throw BusinessException(ErrorCode.FAIL_USE_POINT)
            }
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    fun cancelUsePoint(request: PointUseCancelRequestModel): PointUseCancelResponseModel {
        val userLock = userLockProvider.getUserLock(request.userIdx)
        synchronized(userLock) {
            try {
                val pointTxDto = pointTxProvider.cancelTxAndGetDto(request.userIdx, request.txIdx)
                val pointUseHistories = pointUseHistoryProvider.cancelAndGetCancelDto(pointTxDto.pointHistoryIds)
                pointChargeProvider.cancelUsePoint(pointUseHistories)
                val pointDto = pointProvider.useCancelAndGetDto(request.userIdx, pointTxDto.points)
                return PointUseCancelResponseModel(
                    userIdx = request.userIdx,
                    cancelPoints = pointTxDto.points,
                    remainPoints = pointDto.remainPoints
                )
            } catch (e: BusinessException) {
                throw e
            } catch (e: Exception) {
                logger.error(e.message)
                throw BusinessException(ErrorCode.FAIL_CANCEL_TX)
            }
        }
    }

    fun pointValidCheck(usePoints: Long, pointCharges: List<PointCharge>): Boolean {
        val remainPoints = pointCharges.sumOf { it.remainPoints }
        return usePoints <= remainPoints
    }
}
