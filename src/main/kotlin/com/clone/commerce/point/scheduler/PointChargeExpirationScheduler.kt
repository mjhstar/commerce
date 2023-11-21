package com.clone.commerce.point.scheduler

import com.clone.commerce.point.provider.PointChargeProvider
import com.clone.commerce.point.provider.PointProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.ConcurrentHashMap

@Component
class PointChargeExpirationScheduler(
    private val pointChargeProvider: PointChargeProvider,
    private val pointProvider: PointProvider
) {
    private val batchSize = 100
    private val pageSize = 100

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    fun expirePoint() {
        val expiredPointIds = getExpiredPointIds()
        val expiredPointChunks = expiredPointIds.chunked(batchSize)
        runBlocking(Dispatchers.IO) {
            expiredPointChunks.forEach { chunk ->
                expirePoint(chunk)
            }
        }
    }

    private suspend fun expirePoint(chargePointIds: List<Long>) {
        val pointMap = ConcurrentHashMap<Long, Long>()
        val expiredPoints = pointChargeProvider.getPointChargeEntityList(chargePointIds)
        val updatePoints = expiredPoints.map { point ->
            point.expire()
            pointMap.merge(point.user.userIdx, point.remainPoints) { oldPoints, newPoints ->
                oldPoints + newPoints
            }
            point
        }
        pointProvider.updateExpirePoint(pointMap)
        pointChargeProvider.updateExpirePoint(updatePoints)
    }

    private fun getExpiredPointIds(): List<Long> {
        return generateSequence(0) { it + 1 }
            .map { page ->
                pointChargeProvider.getExpireChargePointIds(page, pageSize)
            }
            .takeWhile { it.isNotEmpty() }
            .flatMap { it }.toList()
    }
}