package com.clone.commerce.point.repository

import com.clone.commerce.point.entity.Point
import org.springframework.data.jpa.repository.JpaRepository

interface PointRepository : JpaRepository<Point, Long> {
    fun findByUserUserIdxIn(userIdx: List<Long>): List<Point>
    fun findByUserUserIdx(userIdx: Long): Point?
}