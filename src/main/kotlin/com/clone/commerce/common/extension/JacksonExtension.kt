package com.clone.commerce.common.extension

import com.clone.commerce.common.extension.Jackson.mapper
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

object Jackson {
    private val DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val mapper: ObjectMapper = createDefaultMapper()

    private fun createDefaultMapper(): ObjectMapper {
        return ObjectMapper().registerKotlinModule().apply {
            registerModule(JavaTimeModule().apply {
                addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(DATETIME_FORMATTER))
                addSerializer(LocalDate::class.java, LocalDateSerializer(DATE_FORMATTER))
                addSerializer(
                    OffsetDateTime::class.java, OffsetDateTimeSerializer()
                )
                addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(DATETIME_FORMATTER))
                addDeserializer(LocalDate::class.java, LocalDateDeserializer(DATE_FORMATTER))
                addDeserializer(OffsetDateTime::class.java, OffsetDateTimeDeserializer())
                addDeserializer(OffsetDateTime::class.java, InstantDeserializer.OFFSET_DATE_TIME)
            })
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }
    }

    fun mapper(): ObjectMapper {
        return mapper
    }
}

class OffsetDateTimeSerializer : JsonSerializer<OffsetDateTime>() {
    override fun serialize(
        value: OffsetDateTime, gen: JsonGenerator, serializers: SerializerProvider
    ) = gen.writeString(value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))

    override fun handledType() = OffsetDateTime::class.java
}

class OffsetDateTimeDeserializer : JsonDeserializer<OffsetDateTime>() {
    private val formatter =
        DateTimeFormatterBuilder().append(DateTimeFormatter.ISO_LOCAL_DATE_TIME).appendPattern("[XXX][XX][X]")
            .toFormatter()

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): OffsetDateTime =
        OffsetDateTime.parse(p.text, formatter)

    override fun handledType() = OffsetDateTime::class.java
}

fun Any.toJson() = mapper().writeValueAsString(this)

// Convert an object to a Map
fun <T> T.toMap(): Map<String, Any> {
    return convert()
}

// Convert an object of type T to type R
inline fun <T, reified R> T.convert(): R {
    return mapper().convertValue(this, R::class.java)
}

fun Any?.toJsonSafely(): String = try {
    this?.let { mapper().writeValueAsString(it) } ?: ""
} catch (e: Exception) {
    ""
}

inline fun <reified T> String.toModel(): T = mapper().readValue(this, jacksonTypeRef<T>())

