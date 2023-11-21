package com.clone.commerce.point.entity

import com.clone.commerce.common.support.extension.TimeUtils
import com.clone.commerce.point.enums.PointUseState
import javax.persistence.*

@Entity(name = "pointUseHistory")
class PointUseHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pointChargeIdx")
    val pointCharge: PointCharge,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pointTransaction_txIdx")
    val pointTx: PointTx,

    @Enumerated(EnumType.STRING)
    var state: PointUseState,

    val points: Long,
    val createdAt: Long = TimeUtils.currentTimeMillis(),
    var updatedAt: Long? = null,
) {
    fun useCancel() {
        this.apply {
            this.state = PointUseState.CANCEL
            this.updatedAt = TimeUtils.currentTimeMillis()
        }
    }

    companion object {
        fun createUseHistory(pointCharge: PointCharge, pointTx: PointTx, amount: Long): PointUseHistory {
            return PointUseHistory(
                pointCharge = pointCharge,
                pointTx = pointTx,
                points = amount,
                state = PointUseState.USED
            )
        }
    }
}