package com.clone.commerce.point.model.request

import com.clone.commerce.common.support.extension.TimeUtils
import com.clone.commerce.common.support.extension.toLocalDate
import com.clone.commerce.common.web.exception.BusinessException
import com.clone.commerce.common.web.exception.ErrorCode
import com.clone.commerce.point.enums.PointTxState
import java.time.LocalDate

class PointGetHistoriesRequest(
    val type: PointTxState?,
    val from: String?,
    val to: String?,
    val size: Int?,
    val page: Int?,
) {
    fun toModel(userIdx: Long): PointGetHistoriesRequestModel {
        val convertFrom: LocalDate?
        val convertTo: LocalDate?

        try {
            convertFrom = from?.toLocalDate()
            convertTo = to?.toLocalDate()
        } catch (e: Exception) {
            throw BusinessException(ErrorCode.FAIL_CONVERT_DATE)
        }

        if (convertFrom != null && convertFrom.plusMonths(6) < convertTo) {
            throw BusinessException(ErrorCode.FAIL_INQUIRY_DATE)
        }

        if (size != null && size > 100) {
            throw BusinessException(ErrorCode.TOO_LARGE_PAGE_SIZE)
        }

        return PointGetHistoriesRequestModel(
            userIdx = userIdx,
            type = this.type,
            from = convertFrom ?: TimeUtils.currentLocalDate().minusMonths(3),
            to = convertTo ?: TimeUtils.currentLocalDate(),
            size = this.size,
            page = this.page

        )
    }
}