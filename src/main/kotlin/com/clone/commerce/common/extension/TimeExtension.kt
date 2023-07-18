package com.clone.commerce.common.extension

import java.time.*
import java.time.format.DateTimeFormatter

private val ZONE_ID_ASIA_SEOUL = ZoneId.of("Asia/Seoul")
private val ZONE_OFFSET_ASIA_SEOUL = ZoneOffset.ofHours(9)

object TimeUtils {
    fun currentTimeMillis() = System.currentTimeMillis()
    fun currentTime() = ZonedDateTime.now(ZONE_ID_ASIA_SEOUL)
    fun currentOffsetDateTime() = currentTimeMillis().toOffsetDateTime()
}
fun LocalDateTime.toOffsetDateTimeKst(): OffsetDateTime = this.atOffset(ZONE_ID_ASIA_SEOUL.rules.getOffset(this))

fun OffsetDateTime?.ifEmptyNowKst(): OffsetDateTime {
    return this ?: OffsetDateTime.now(ZONE_ID_ASIA_SEOUL)
}

fun String.toOffsetDateTime(): OffsetDateTime = OffsetDateTime.parse(this)

fun OffsetDateTime.toKst(): OffsetDateTime {
    return this.toInstant().atOffset(ZONE_OFFSET_ASIA_SEOUL)
}

fun LocalDateTime.toIsoOffsetDateTimeString() =
    this.atOffset(ZONE_ID_ASIA_SEOUL.rules.getOffset(this)).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

fun OffsetDateTime.isBeforeOrEqual(compareTime: OffsetDateTime): Boolean {
    return (this.isBefore(compareTime) || this.isEqual(compareTime))
}

fun OffsetDateTime.isAfterOrEqual(compareTime: OffsetDateTime): Boolean {
    return (this.isAfter(compareTime) || this.isEqual(compareTime))
}

fun OffsetDateTime.toIsoDateTimeString() = this.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

fun OffsetDateTime.toKstStringFormat(): String {
    return this.toKst().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
}

fun OffsetDateTime.toKstStringDBSyncFormat(): String {
    return this.toKst().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}

fun LocalDateTime.toDateTimeFormat() = this.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))

fun OffsetDateTime.toKstMin(): OffsetDateTime {
    return this.toLocalDate().atTime(LocalTime.MIN.atOffset(ZONE_OFFSET_ASIA_SEOUL))
}

fun OffsetDateTime.toKstMax(): OffsetDateTime {
    return this.toLocalDate().atTime(LocalTime.MAX.atOffset(ZONE_OFFSET_ASIA_SEOUL))
}

fun Long.toOffsetDateTime(): OffsetDateTime{
    val instant = Instant.ofEpochMilli(this)
    return OffsetDateTime.ofInstant(instant, ZONE_ID_ASIA_SEOUL)
}