package com.clone.commerce.point.repository

import com.clone.commerce.point.entity.PointCharge
import com.clone.commerce.point.enums.PointState
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PointChargeRepository : JpaRepository<PointCharge, Long> {
    fun findAllByIdxIn(ids: List<Long>): List<PointCharge>

    @Query("SELECT pc FROM pointCharge pc WHERE pc.user.userIdx = :userIdx AND pc.state = :state AND pc.expireAt > :today")
    fun findOfAllMyCharges(userIdx: Long, state: PointState, today: Long, pageable: Pageable): List<PointCharge>

    @Query("SELECT pc.idx FROM pointCharge pc WHERE pc.state != :state AND pc.expireAt <= :expireAt")
    fun findIdsOfExpirePoint(state: PointState, expireAt: Long, pageable: Pageable): List<Long>
}
