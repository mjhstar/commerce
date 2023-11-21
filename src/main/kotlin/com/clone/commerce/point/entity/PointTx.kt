package com.clone.commerce.point.entity

import com.clone.commerce.common.support.extension.TimeUtils
import com.clone.commerce.point.enums.PointTxState
import com.clone.commerce.user.entity.User
import javax.persistence.*

@Entity(name = "pointTransaction")
class PointTx(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val txIdx: Long = 0L,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userIdx")
    val user: User,

    @Enumerated(EnumType.STRING)
    var state: PointTxState,

    @OneToOne(mappedBy = "pointTx", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val pointCharge: PointCharge? = null,

    @OneToMany(mappedBy = "pointTx", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val pointUesHistories: List<PointUseHistory> = emptyList(),

    val points: Long,
    val createdAt: Long = TimeUtils.currentTimeMillis(),
    val usedAt: Long? = null,
    var canceledAt: Long? = null
) {
    fun cancel(): PointTx {
        return this.apply {
            this.state = PointTxState.CANCELED
            this.canceledAt = TimeUtils.currentTimeMillis()
        }
    }
}