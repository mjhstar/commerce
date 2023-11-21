package com.clone.commerce.point.model.response

class PointUseCancelResponse(
    val userIdx: Long,
    val cancelPoints: Long,
    val remainPoints: Long
) {
    companion object {
        fun createBy(model: PointUseCancelResponseModel): PointUseCancelResponse {
            return PointUseCancelResponse(
                userIdx = model.userIdx,
                cancelPoints = model.cancelPoints,
                remainPoints = model.remainPoints
            )
        }
    }
}