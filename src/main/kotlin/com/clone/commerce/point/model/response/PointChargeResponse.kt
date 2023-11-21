package com.clone.commerce.point.model.response

class PointChargeResponse(
    val txIdx: Long,
    val userIdx: Long,
    val remainPoints: Long
) {
    companion object {
        fun createBy(model: PointChargeResponseModel) = PointChargeResponse(
            txIdx = model.txIdx,
            userIdx = model.userIdx,
            remainPoints = model.remainPoints
        )
    }
}