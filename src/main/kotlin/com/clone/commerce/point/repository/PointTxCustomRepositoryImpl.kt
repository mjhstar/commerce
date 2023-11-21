package com.clone.commerce.point.repository

import com.clone.commerce.common.support.extension.toMillis
import com.clone.commerce.common.support.jpa.KotlinQuerydslRepositorySupport
import com.clone.commerce.point.entity.PointTx
import com.clone.commerce.point.entity.QPointTx
import com.clone.commerce.point.model.condition.PointTxCondition
import com.clone.commerce.point.model.dto.PointTxResponseDto
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Repository

@Repository
class PointTxCustomRepositoryImpl : KotlinQuerydslRepositorySupport(PointTx::class), PointTxCustomRepository {
    private val pointTx = QPointTx.pointTx

    private fun makeTable(): JPAQuery<PointTx> {
        val queryFactory = JPAQueryFactory(entityManager)
        return queryFactory
            .selectFrom(pointTx)
    }

    private fun makeWhere(condition: PointTxCondition): BooleanBuilder {
        val booleanBuilder = BooleanBuilder().apply {
            this.and(pointTx.user.userIdx.eq(condition.userIdx))

            if (condition.type != null) {
                this.and(pointTx.state.eq(condition.type))
            }

            if (condition.from != null && condition.to != null) {
                this.and(pointTx.createdAt.between(condition.from.toMillis(), condition.to.plusDays(1).toMillis()))
            }
        }
        return booleanBuilder
    }

    override fun searchPointTxList(condition: PointTxCondition): Page<PointTxResponseDto> {
        val pageable = condition.getPager()
        val where = makeWhere(condition)
        val query = makeTable().where(where).offset(pageable.offset).limit(pageable.pageSize.toLong()).fetch().map {
            PointTxResponseDto.createBy(it)
        }
        return PageImpl(query, pageable, query.size.toLong())
    }


}