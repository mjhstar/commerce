package com.clone.coupangclone.common.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.companionObject

fun <T : Any> getClassForLogging(javaClass: Class<T>): Class<*> = javaClass.enclosingClass?.takeIf {
    it.kotlin.companionObject?.java == javaClass
} ?: javaClass

class LoggerDelegate<in R : Any> : ReadOnlyProperty<R, Logger> {
    override fun getValue(thisRef: R, property: KProperty<*>): Logger = getLogger(
        getClassForLogging(
            thisRef.javaClass
        )
    )
}

open class LoggingCompanion {
    protected val logger by LoggerDelegate()
}

val dollarSignRegex = """\$.*$""".toRegex()

private fun <T : Any> getClassName(clazz: Class<T>): String = clazz.name.replace(dollarSignRegex, "")

fun topLevelLogging(lambda: () -> Unit): Lazy<Logger> = lazy { getLogger(getClassName(lambda.javaClass)) }
