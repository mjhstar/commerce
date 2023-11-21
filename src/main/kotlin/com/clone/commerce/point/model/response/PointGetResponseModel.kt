package com.clone.commerce.point.model.response

import com.clone.commerce.point.model.dto.PointDto

class PointGetResponseModel(
    val userIdx: Long,
    val remainPoints: Long
) {
    companion object {
        fun createBy(point: PointDto) = PointGetResponseModel(
            userIdx = point.userIdx,
            remainPoints = point.remainPoints
        )
    }
}