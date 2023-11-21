package com.clone.commerce.point.model.response

class PointUseResponse(
    val txIdx: Long,
    val userIdx: Long,
    val usePoints: Long,
    val remainPoints: Long
) {
    companion object {
        fun createBy(model: PointUseResponseModel) = PointUseResponse(
            txIdx = model.txIdx,
            userIdx = model.userIdx,
            usePoints = model.usePoints,
            remainPoints = model.remainPoints
        )
    }
}