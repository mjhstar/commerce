package com.clone.commerce.point.model.response

class PointGetResponse(
    val userIdx: Long,
    val remainPoints: Long
) {
    companion object{
        fun createBy(model: PointGetResponseModel) = PointGetResponse(
            userIdx = model.userIdx,
            remainPoints = model.remainPoints
        )
    }
}