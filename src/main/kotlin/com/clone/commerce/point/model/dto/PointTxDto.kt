package com.clone.commerce.point.model.dto

import com.clone.commerce.common.support.extension.toOffsetDateTime
import com.clone.commerce.point.entity.PointTx
import com.clone.commerce.point.enums.PointTxState
import java.time.OffsetDateTime

class PointTxDto(
    val txIdx: Long,
    val userIdx: Long,
    val state: PointTxState,
    val pointChargeIdx: Long?,
    val pointHistoryIds: List<Long>,
    val points: Long,
    val createdAt: OffsetDateTime,
    val usedAt: OffsetDateTime?,
    val canceledAt: OffsetDateTime?
) {
    companion object {
        fun createBy(pointTx: PointTx): PointTxDto {
            return PointTxDto(
                txIdx = pointTx.txIdx,
                userIdx = pointTx.user.userIdx,
                state = pointTx.state,
                pointChargeIdx = pointTx.pointCharge?.idx,
                pointHistoryIds = pointTx.pointUesHistories.map { it.idx },
                points = pointTx.points,
                createdAt = pointTx.createdAt.toOffsetDateTime(),
                usedAt = pointTx.usedAt?.toOffsetDateTime(),
                canceledAt = pointTx.canceledAt?.toOffsetDateTime()
            )
        }
    }
}