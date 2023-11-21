package com.clone.commerce.point.entity

import com.clone.commerce.common.support.extension.TimeUtils
import com.clone.commerce.user.entity.User
import javax.persistence.*

@Entity(name = "point")
class Point(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long = 0L,

    @OneToOne
    @JoinColumn(name = "userIdx")
    val user: User,

    var remainPoints: Long,
    var updatedAt: Long? = null
) {
    fun usePoint(usePoints: Long): Point {
        return this.apply {
            this.remainPoints -= usePoints
            this.updatedAt = TimeUtils.currentTimeMillis()
        }
    }

    fun chargePoint(chargePoints: Long): Point {
        return this.apply {
            this.remainPoints += chargePoints
            this.updatedAt = TimeUtils.currentTimeMillis()
        }
    }

    fun cancelUsePoint(usedPoints: Long): Point {
        return this.apply {
            this.remainPoints += usedPoints
            this.updatedAt = TimeUtils.currentTimeMillis()
        }
    }

    fun expirePoint(expiredPoints: Long): Point {
        return this.apply {
            this.remainPoints -= expiredPoints
            this.updatedAt = TimeUtils.currentTimeMillis()
        }
    }
}