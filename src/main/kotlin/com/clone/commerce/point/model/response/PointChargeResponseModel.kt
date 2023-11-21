package com.clone.commerce.point.model.response

import com.clone.commerce.point.entity.PointTx
import com.clone.commerce.point.model.dto.PointDto

class PointChargeResponseModel(
    val txIdx: Long,
    val userIdx: Long,
    val remainPoints: Long
) {
    companion object {
        fun createBy(pointTx: PointTx, pointDto: PointDto) = PointChargeResponseModel(
            txIdx = pointTx.txIdx,
            userIdx = pointDto.userIdx,
            remainPoints = pointDto.remainPoints
        )
    }
}