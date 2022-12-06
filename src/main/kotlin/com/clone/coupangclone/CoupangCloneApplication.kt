package com.clone.coupangclone

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan("com.clone.coupangclone")
class CoupangCloneApplication

fun main(args: Array<String>) {
    runApplication<CoupangCloneApplication>(*args)
}
