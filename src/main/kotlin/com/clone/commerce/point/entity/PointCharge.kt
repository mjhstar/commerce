package com.clone.commerce.point.entity

import com.clone.commerce.common.support.extension.TimeUtils
import com.clone.commerce.common.support.extension.plusYear
import com.clone.commerce.point.enums.PointState
import com.clone.commerce.user.entity.User
import javax.persistence.*

@Entity(name = "pointCharge")
class PointCharge(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long = 0L,
    val chargePoints: Long,
    var remainPoints: Long,

    @Enumerated(EnumType.STRING)
    var state: PointState,

    @OneToMany(mappedBy = "pointCharge", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val pointUesHistories: List<PointUseHistory> = emptyList(),

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "pointTx_txIdx")
    val pointTx: PointTx,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx")
    val user: User,

    val expireAt: Long,
    val createdAt: Long = TimeUtils.currentTimeMillis(),
    var updatedAt: Long? = null,
) {
    constructor(chargePoint: Long, user: User, pointTx: PointTx) : this(
        chargePoints = chargePoint,
        remainPoints = chargePoint,
        state = PointState.ACTIVE,
        expireAt = TimeUtils.todayMillis().plusYear(1),
        user = user,
        pointTx = pointTx
    )

    fun usePoint(usedPoint: Long) {
        this.apply {
            this.remainPoints -= usedPoint
            this.state = if (this.remainPoints > 0) PointState.ACTIVE else PointState.USED
            this.updatedAt = TimeUtils.currentTimeMillis()
        }
    }

    fun cancelUsePoint(usedPoint: Long) {
        this.apply {
            this.remainPoints += usedPoint
            if (this.state == PointState.USED) {
                this.state = PointState.ACTIVE
            }
            this.updatedAt = TimeUtils.currentTimeMillis()

        }
    }

    fun expire() {
        this.apply {
            this.state = PointState.EXPIRED
            this.updatedAt = TimeUtils.currentTimeMillis()
        }
    }
}