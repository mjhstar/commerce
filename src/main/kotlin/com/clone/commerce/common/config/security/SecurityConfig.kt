package com.clone.commerce.common.config.security

import com.clone.commerce.user.service.JwtTokenProvider
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
    private val accessDeniedHandler: AccessDeniedHandler,
    private val jwtTokenProvider: JwtTokenProvider
): WebSecurityConfigurerAdapter() {
    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return CustomAuthenticationManager()
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
            .addFilter(BasicAuthFilter(authenticationManager(), jwtTokenProvider))
            .authorizeRequests()
            .antMatchers(
//                "/test-alive",
                "/user/v1/**"
            ).permitAll()
            .anyRequest()
            .authenticated()
    }
}
