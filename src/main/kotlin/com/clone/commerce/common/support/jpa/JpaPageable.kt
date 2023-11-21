package com.clone.commerce.common.support.jpa

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

class JpaPageable(
    override val page: Int,
    override val size: Int,
    private val sort: Sort = Sort.unsorted()
) : Pageable, PagerInfo {
    override fun getPageNumber(): Int = page

    override fun getPageSize(): Int = size

    override fun getOffset(): Long = pageSize.toLong() * (page - 1L)

    override fun getSort(): Sort = sort

    override fun next(): Pageable = JpaPageable(page + 1, size)

    override fun previousOrFirst(): Pageable = if (page <= 1) first() else JpaPageable(page - 1, size)

    override fun first(): Pageable = JpaPageable(1, size)

    override fun withPage(pageNumber: Int): Pageable = JpaPageable(pageNumber, size)

    override fun hasPrevious(): Boolean = page > 1
}