package com.clone.commerce.point.repository

import com.clone.commerce.point.entity.PointTx
import com.clone.commerce.point.enums.PointTxState
import org.springframework.data.jpa.repository.JpaRepository

interface PointTxRepository : JpaRepository<PointTx, Long> {
    fun findByTxIdxAndUserUserIdxAndState(txIdx: Long, userIdx: Long, state: PointTxState): PointTx?
}

fun PointTxRepository.findUseTx(txIdx: Long, userIdx: Long): PointTx? {
    return findByTxIdxAndUserUserIdxAndState(txIdx, userIdx, PointTxState.USED)
}
