package com.clone.commerce.point.provider

import com.clone.commerce.common.web.exception.BusinessException
import com.clone.commerce.common.web.exception.ErrorCode
import com.clone.commerce.point.entity.Point
import com.clone.commerce.point.model.dto.PointDto
import com.clone.commerce.point.repository.PointRepository
import com.clone.commerce.user.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component

@Component
class PointProvider(
    private val pointRepository: PointRepository,
) {
    fun getPoint(userIdx: Long): PointDto {
        val point = pointRepository.findByUserUserIdx(userIdx)
        return point?.let { PointDto.createBy(it) } ?: PointDto(userIdx = userIdx, remainPoints = 0)
    }

    fun getPointEntity(userIdx: Long): Point {
        return pointRepository.findByUserUserIdx(userIdx) ?: throw BusinessException(ErrorCode.NOT_EXIST_USER)
    }

    fun usePointAndGetEntity(userIdx: Long, usePoints: Long): Point {
        val point = getPointEntity(userIdx)
        point.usePoint(usePoints)
        return pointRepository.save(point)
    }

    fun chargePointAndGetDto(user: User, chargePoints: Long): PointDto {
        val point = pointRepository.findByUserUserIdx(user.userIdx)?.chargePoint(chargePoints)
            ?: Point(
                remainPoints = chargePoints,
                user = user
            )
        return PointDto.createBy(pointRepository.save(point))
    }

    fun useCancelAndGetDto(userIdx: Long, usedPoints: Long): PointDto {
        val point = pointRepository.findByUserUserIdx(userIdx)?.cancelUsePoint(usedPoints)
            ?: throw BusinessException(ErrorCode.FAIL_CANCEL_TX)
        return PointDto.createBy(point)
    }

    fun isAvailableUsePoint(userIdx: Long, usePoints: Long): Boolean {
        val point = getPoint(userIdx)
        return point.remainPoints >= usePoints
    }

    fun updateExpirePoint(pointMap: Map<Long, Long>) {
        val batchSize = 100
        val pointIds = pointMap.keys.toList()
        val chunkedIds = pointIds.chunked(batchSize)

        runBlocking {
            val updateTasks = chunkedIds.map { batchIds ->
                async(Dispatchers.IO) {
                    val points = pointRepository.findByUserUserIdxIn(batchIds)
                    val updatePoints = points.map { point ->
                        point.expirePoint(pointMap[point.user.userIdx] ?: 0)
                        point
                    }
                    pointRepository.saveAll(updatePoints)
                }
            }
            updateTasks.awaitAll()
        }
    }
}