package com.clone.commerce.point.repository

import com.clone.commerce.point.model.condition.PointTxCondition
import com.clone.commerce.point.model.dto.PointTxResponseDto
import org.springframework.data.domain.Page

interface PointTxCustomRepository {
    fun searchPointTxList(condition: PointTxCondition): Page<PointTxResponseDto>
}