package com.clone.commerce.point.provider

import com.clone.commerce.common.support.extension.TimeUtils
import com.clone.commerce.common.web.exception.BusinessException
import com.clone.commerce.common.web.exception.ErrorCode
import com.clone.commerce.point.entity.PointTx
import com.clone.commerce.point.enums.PointTxState
import com.clone.commerce.point.model.dto.PointTxDto
import com.clone.commerce.point.model.dto.PointTxResponseDto
import com.clone.commerce.point.model.request.PointGetHistoriesRequestModel
import com.clone.commerce.point.repository.PointTxCustomRepository
import com.clone.commerce.point.repository.PointTxRepository
import com.clone.commerce.point.repository.findUseTx
import com.clone.commerce.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class PointTxProvider(
    private val pointTxRepository: PointTxRepository,
    private val pointTxCustomRepository: PointTxCustomRepository
) {
    fun createPointTxAndGetEntity(user: User, points: Long, state: PointTxState): PointTx {
        val pointTx = PointTx(
            user = user,
            points = points,
            state = state,
            usedAt = if (state == PointTxState.USED) TimeUtils.currentTimeMillis() else null
        )
        return pointTxRepository.save(pointTx)
    }

    fun cancelTxAndGetDto(userIdx: Long, txIdx: Long): PointTxDto {
        val pointTx = pointTxRepository.findUseTx(txIdx, userIdx)
            ?: throw BusinessException(ErrorCode.NOT_EXIST_TX)
        return PointTxDto.createBy(pointTxRepository.save(pointTx.cancel()))
    }

    fun getHistories(request: PointGetHistoriesRequestModel): Page<PointTxResponseDto> {
        return pointTxCustomRepository.searchPointTxList(request.toCondition())
    }
}