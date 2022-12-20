package com.clone.commerce.common.extension

import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

private val ZONE_ID_ASIA_SEOUL = ZoneId.of("Asia/Seoul")
private val ZONE_OFFSET_ASIA_SEOUL = ZoneOffset.ofHours(9)

object TimeUtils {
    fun currentTimeMillis() = System.currentTimeMillis()
    fun currentTime() = ZonedDateTime.now(ZONE_ID_ASIA_SEOUL)
}
