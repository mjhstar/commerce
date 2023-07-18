package com.clone.commerce.common.config.converter

import java.time.LocalDateTime
import java.time.OffsetDateTime
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class OffsetDateTimeConverter: AttributeConverter<OffsetDateTime, LocalDateTime> {
    override fun convertToDatabaseColumn(attribute: OffsetDateTime?): LocalDateTime {
        TODO()
    }

    override fun convertToEntityAttribute(dbData: LocalDateTime?): OffsetDateTime {
        TODO("Not yet implemented")
    }
}