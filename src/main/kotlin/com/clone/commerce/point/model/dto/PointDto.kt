package com.clone.commerce.point.model.dto

import com.clone.commerce.point.entity.Point

class PointDto(
    val userIdx: Long,
    val remainPoints: Long
) {
    companion object {
        fun createBy(point: Point) = PointDto(
            userIdx = point.user.userIdx,
            remainPoints = point.remainPoints
        )
    }
}