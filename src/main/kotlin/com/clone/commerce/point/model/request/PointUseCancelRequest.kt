package com.clone.commerce.point.model.request

class PointUseCancelRequest(
    val txIdx: Long
) {
    fun toModel(userIdx: Long) = PointUseCancelRequestModel(
        userIdx = userIdx,
        txIdx = this.txIdx
    )
}