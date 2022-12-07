package com.clone.coupangclone.common.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authEntryPointHandler: AuthEntryPointHandler,
    private val accessDeniedHandler: AccessDeniedHandler
): WebSecurityConfigurerAdapter() {
    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }


    @Throws(Exception::class)
    override fun configure(security: HttpSecurity) {
        security
            .httpBasic().disable()
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(authEntryPointHandler)
            .accessDeniedHandler(accessDeniedHandler)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            //TODO
            .addFilter(BasicAuthFilter(authenticationManager()))
            .authorizeRequests()
            .antMatchers(
                "/test-alive",
                "/user/v1/*"
            ).permitAll()
            .anyRequest()
            .authenticated()
    }
}
