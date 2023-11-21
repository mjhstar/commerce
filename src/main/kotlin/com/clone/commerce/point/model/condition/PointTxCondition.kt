package com.clone.commerce.point.model.condition

import com.clone.commerce.common.support.jpa.JpaPageable
import com.clone.commerce.point.enums.PointTxState
import org.springframework.data.domain.Pageable
import java.time.LocalDate

class PointTxCondition(
    val userIdx: Long,
    val type: PointTxState? = null,
    val from: LocalDate? = null,
    val to: LocalDate? = null,
    val size: Int? = null,
    val page: Int? = null,
) {
    fun getPager(): Pageable {
        return JpaPageable(
            page = this.page ?: 1,
            size = this.size ?: 20
        )
    }
}