package com.clone.commerce.point.model.response

class PointUseResponseModel(
    val txIdx: Long,
    val userIdx: Long,
    val usePoints: Long,
    val remainPoints: Long
) {
}