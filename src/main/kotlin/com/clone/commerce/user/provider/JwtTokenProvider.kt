package com.clone.commerce.user.provider

import com.clone.commerce.common.config.security.Keys
import com.clone.commerce.common.support.extension.TimeUtils
import com.clone.commerce.common.web.exception.BusinessException
import com.clone.commerce.common.web.exception.ErrorCode
import com.clone.commerce.user.entity.User
import com.clone.commerce.user.model.AccessTokenParam
import com.clone.commerce.user.model.RefreshTokenParam
import com.clone.commerce.user.repository.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.servlet.http.HttpServletRequest
import javax.xml.bind.DatatypeConverter

@Component
class JwtTokenProvider(
    private val userRepository: UserRepository
) {
    fun createAccessToken(param: AccessTokenParam): String {
        val signKey =
            SecretKeySpec(DatatypeConverter.parseBase64Binary(Keys.GENERAL_KEY), SignatureAlgorithm.HS256.jcaName)
        return Jwts.builder().setHeaderParam("type", "JWT").claim("userIdx", param.userIdx).claim("type", param.type)
            .claim("email", param.email).claim("createdAt", param.createdAt).claim("key", param.key).setIssuer("jhmoon")
            .signWith(SignatureAlgorithm.HS256, signKey)
            .setExpiration(Date.from(TimeUtils.currentTime().plusHours(1).toInstant())).compact()
    }

    fun createRefreshToken(param: RefreshTokenParam): String {
        val signKey =
            SecretKeySpec(DatatypeConverter.parseBase64Binary(Keys.GENERAL_KEY), SignatureAlgorithm.HS256.jcaName)
        return Jwts.builder().setIssuer("jhmoon").claim("email", param.email).claim("createdAt", param.createdAt)
            .claim("key", param.key)
            .signWith(SignatureAlgorithm.HS256, signKey).compact()
    }

    fun getBaseTime(request: HttpServletRequest): Long {
        return try {
            var authorizeTime: Long = request.getHeader("AuthorizeTime").toLong()
            if (authorizeTime == 0L) {
                authorizeTime = System.currentTimeMillis()
            }
            if (authorizeTime > 1600000000000L) {
                authorizeTime /= 1000L
            }
            authorizeTime
        } catch (e: Exception) {
            System.currentTimeMillis() / 1000L
        }
    }

    fun getUser(token: String): User? {
        if (token.isBlank()) {
            return null
        }
        val claims = Jwts.parser().setSigningKey(Keys.GENERAL_KEY).parseClaimsJws(token).body
        val issuer = claims.issuer
        if (issuer != "jhmoon") {
            throw BusinessException(ErrorCode.MISMATCH_TOKEN_ISSUER)
        }
        val email = claims["email"].toString()
        val createdAt = claims["createdAt"].toString().toLong()

        val accessKey = claims["key"].toString()
        val user = userRepository.findByEmail(email)
        val refreshKey = getKey(user?.refreshToken ?: "")
        if (accessKey != refreshKey) {
            throw BusinessException(ErrorCode.INVALID_USER_TOKEN)
        }
        return user;
    }

    fun getKey(token: String): String? {
        if (token.isBlank()) return null
        val claims = Jwts.parser().setSigningKey(Keys.GENERAL_KEY).parseClaimsJws(token).body
        return claims["key"].toString()
    }
}
