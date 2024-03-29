package com.clone.commerce.common.config.security

import com.clone.commerce.common.web.exception.BusinessException
import com.clone.commerce.common.web.exception.ErrorCode
import com.clone.commerce.user.provider.JwtTokenProvider
import io.jsonwebtoken.ExpiredJwtException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

class BasicAuthFilter(
    authenticationManager: AuthenticationManager,
    val jwtTokenProvider: JwtTokenProvider
) : BasicAuthenticationFilter(authenticationManager) {
    private val BEARER_TOKEN = "Bearer"
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authentication = getAuthentication(request)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

    fun getAuthentication(request: HttpServletRequest): Authentication? {
        return try {
            val token = getToken(request)
            val baseTime = jwtTokenProvider.getBaseTime(request)
            val user = jwtTokenProvider.getUser(token)
            UsernamePasswordAuthenticationToken(user, null)
        } catch (e: Exception) {
            when (e) {
                is ExpiredJwtException -> throw BusinessException(ErrorCode.INVALID_USER_TOKEN)
                else -> null
            }
        }
    }

    fun getToken(request: HttpServletRequest): String {
        return try {
            val token = request.getHeader("Authorization")
            token.substringAfter(BEARER_TOKEN).trim()
        } catch (e: Exception) {
            ""
        }
    }
}
