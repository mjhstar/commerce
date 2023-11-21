package com.clone.commerce.point.model.request

class PointChargeRequest(
    val chargePoints: Long,
) {
    fun toModel(userIdx: Long) = PointChargeRequestModel(
        userIdx = userIdx,
        chargePoints = this.chargePoints
    )
}