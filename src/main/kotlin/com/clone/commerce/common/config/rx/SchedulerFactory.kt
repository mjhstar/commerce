package com.clone.commerce.common.config.rx

import com.google.common.util.concurrent.ThreadFactoryBuilder
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Executors.newFixedThreadPool
import java.util.concurrent.ThreadFactory

enum class SchedulerFactory(
    val type: String,
    val count: Int,
    val scheduler: Scheduler
) {
    GLOBAL("GLOBAL", 200, Schedulers.from(newFixedThreadPool(100, threadFactory("scheduler-GLOBAL-%d")))),


    ;


}

fun threadFactory(pattern: String): ThreadFactory {
    return ThreadFactoryBuilder()
        .setNameFormat(pattern)
        .build()
}
