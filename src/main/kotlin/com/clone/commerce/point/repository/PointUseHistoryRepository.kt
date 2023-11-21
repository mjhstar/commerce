package com.clone.commerce.point.repository

import com.clone.commerce.point.entity.PointUseHistory
import org.springframework.data.jpa.repository.JpaRepository

interface PointUseHistoryRepository : JpaRepository<PointUseHistory, Long> {
    fun findByIdxIn(ids: List<Long>): List<PointUseHistory>
}