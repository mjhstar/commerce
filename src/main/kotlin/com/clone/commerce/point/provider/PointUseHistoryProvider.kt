package com.clone.commerce.point.provider

import com.clone.commerce.point.entity.PointCharge
import com.clone.commerce.point.entity.PointTx
import com.clone.commerce.point.entity.PointUseHistory
import com.clone.commerce.point.model.dto.PointCancelDto
import com.clone.commerce.point.repository.PointUseHistoryRepository
import org.springframework.stereotype.Component
import kotlin.math.min

@Component
class PointUseHistoryProvider(
    private val pointUseHistoryRepository: PointUseHistoryRepository
) {
    fun createUseHistories(pointChargeList: List<PointCharge>, usePoints: Long, pointTx: PointTx) {
        val useHistoryList = mutableListOf<PointUseHistory>()
        var remainUsePoint = usePoints
        for (pointCharge in pointChargeList) {
            val availablePoint = pointCharge.remainPoints
            val usedPoints = min(remainUsePoint, availablePoint)

            if (usedPoints > 0) {
                val useHistory = PointUseHistory.createUseHistory(
                    pointCharge = pointCharge,
                    pointTx = pointTx,
                    amount = usedPoints
                )
                pointCharge.usePoint(usedPoints)
                useHistoryList.add(useHistory)
                remainUsePoint -= usedPoints
            }
            if (remainUsePoint <= 0) {
                break
            }
        }
        if (useHistoryList.isNotEmpty()) {
            pointUseHistoryRepository.saveAll(useHistoryList)
        }
    }

    fun cancelAndGetCancelDto(historyIds: List<Long>): List<PointCancelDto> {
        val histories = pointUseHistoryRepository.findByIdxIn(historyIds)
        val dtoList = histories.map { history ->
            history.useCancel()
            PointCancelDto(history.pointCharge.idx, history.points)
        }
        return dtoList
    }

}