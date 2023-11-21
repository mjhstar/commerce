package com.clone.commerce.common.support.jpa

import org.springframework.data.jpa.repository.support.Querydsl
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import kotlin.reflect.KClass

abstract class KotlinQuerydslRepositorySupport : QuerydslRepositorySupport {
    constructor(kClass: KClass<*>) : this(kClass.java)
    constructor(clazz: Class<*>) : super(clazz)

    override fun getQuerydsl(): Querydsl {
        return super.getQuerydsl() ?: throw IllegalStateException("QueryDSL is NULL!")
    }
}
