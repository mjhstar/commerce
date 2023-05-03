package com.clone.commerce.common.config.db

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.Date
import org.bson.UuidRepresentation
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions

@Configuration
class MongoConfig(
    private val mongoProperties: MongoProperties
) : AbstractMongoClientConfiguration() {
    override fun getDatabaseName(): String = mongoProperties.database

    @Bean
    override fun mongoClient() = MongoClients.create(
        MongoClientSettings.builder()
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .applyConnectionString(ConnectionString(mongoProperties.uri))
            .build()
    )

    override fun configureConverters(converterConfigurationAdapter: MongoCustomConversions.MongoConverterConfigurationAdapter) {
        super.configureConverters(converterConfigurationAdapter)
        converterConfigurationAdapter.registerConverters(
            listOf(
                OffsetDateTimeToDateConverter,
                DateToOffsetDateTimeConverter
            )
        )
    }

    object OffsetDateTimeToDateConverter : Converter<OffsetDateTime, Date> {
        override fun convert(source: OffsetDateTime): Date? {
            return Date.from(source.toInstant())
        }
    }

    object DateToOffsetDateTimeConverter : Converter<Date, OffsetDateTime> {
        override fun convert(source: Date): OffsetDateTime? {
            return source.toInstant().atOffset(ZoneOffset.ofHours(9))
        }
    }
}
