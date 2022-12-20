package com.clone.commerce.common.config

import com.clone.commerce.common.config.rx.ObservableReturnValueHandler
import com.clone.commerce.common.config.rx.RxAutoConfiguration
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
@Import(RxAutoConfiguration::class, MultipartAutoConfiguration::class)
class WebMvcConfiguration(
    private val observableReturnValueHandler: ObservableReturnValueHandler
) : WebMvcConfigurer, WebMvcRegistrations {
    @Override
    override fun addReturnValueHandlers(handlers: MutableList<HandlerMethodReturnValueHandler?>) {
        handlers.add(observableReturnValueHandler)
    }
}
