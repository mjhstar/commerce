package com.clone.commerce.common.config.rx

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RxAutoConfiguration {
    @Bean(name = ["rx"])
    fun observableReturnValueHandler(): ObservableReturnValueHandler {
        return ObservableReturnValueHandler()
    }
}
