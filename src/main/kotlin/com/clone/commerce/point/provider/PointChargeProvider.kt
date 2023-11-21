package com.clone.commerce.point.provider

import com.clone.commerce.common.support.extension.TimeUtils
import com.clone.commerce.point.entity.PointCharge
import com.clone.commerce.point.entity.PointTx
import com.clone.commerce.point.enums.PointState
import com.clone.commerce.point.model.dto.PointCancelDto
import com.clone.commerce.point.repository.PointChargeRepository
import com.clone.commerce.user.entity.User
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class PointChargeProvider(
    private val pointChargeRepository: PointChargeRepository,
) {
    fun chargePoint(user: User, chargePoints: Long, pointTx: PointTx) {
        val pointCharge = PointCharge(
            chargePoint = chargePoints,
            user = user,
            pointTx = pointTx
        )
        pointChargeRepository.save(pointCharge)
    }

    fun getPointChargeEntityList(ids: List<Long>): List<PointCharge> {
        return pointChargeRepository.findAllByIdxIn(ids)
    }

    fun getPointChargeEntityList(userIdx: Long, usePoints: Long): List<PointCharge> {
        val batchSize = 10
        val findPointCharges = mutableListOf<PointCharge>()
        generateSequence(0) { it + 1 }
            .map { page ->
                val pointCharges = pointChargeRepository.findOfAllMyCharges(
                    userIdx,
                    PointState.ACTIVE,
                    TimeUtils.currentTimeMillis(),
                    PageRequest.of(page, batchSize)
                )
                findPointCharges.addAll(pointCharges)
                pointCharges
            }
            .takeWhile {
                val currentPoints = usePoints - findPointCharges.sumOf { it.remainPoints }
                currentPoints > 0
            }
            .toList()

        val pointChargeList = mutableListOf<PointCharge>()
        var remainPoints = usePoints
        for (pointCharge in findPointCharges) {
            if (remainPoints <= 0) break
            pointChargeList.add(pointCharge)
            remainPoints -= pointCharge.remainPoints
        }
        return pointChargeList
    }

    fun cancelUsePoint(cancelDtoList: List<PointCancelDto>) {
        val dtoMap = cancelDtoList.associate { it.chargeIdx to it.usedPoints }
        val updatePointCharges = mutableListOf<PointCharge>()
        val pointCharges = pointChargeRepository.findAllByIdxIn(dtoMap.keys.toList())
        pointCharges.forEach {
            it.cancelUsePoint(dtoMap[it.idx] ?: 0)
            updatePointCharges.add(it)
        }
        if (updatePointCharges.isNotEmpty()) {
            pointChargeRepository.saveAll(updatePointCharges)
        }
    }

    fun updateExpirePoint(chargePoints: List<PointCharge>) {
        pointChargeRepository.saveAll(chargePoints)
    }

    fun getExpireChargePointIds(page: Int, pageSize: Int): List<Long> {
        return pointChargeRepository.findIdsOfExpirePoint(
            state = PointState.EXPIRED,
            expireAt = TimeUtils.currentTimeMillis(),
            pageable = PageRequest.of(page, pageSize)
        )
    }

}