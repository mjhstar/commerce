package com.clone.commerce.point.model.request

class PointUseRequest(
    val usePoints: Long
) {
    fun toModel(userIdx: Long) = PointUseRequestModel(
        userIdx = userIdx,
        usePoints = this.usePoints
    )
}