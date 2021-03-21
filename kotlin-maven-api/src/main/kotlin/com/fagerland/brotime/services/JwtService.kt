package com.fagerland.brotime.services

import com.fagerland.brotime.entities.UserEntity
import com.fagerland.brotime.repositories.UserRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.util.Date
import javax.servlet.http.HttpServletRequest

private const val HEADER_TOKEN_INDEX = 7

@Service
class JwtService(
    val userRepository: UserRepository
) {

    fun createJwt(userEntity: UserEntity): String {
        val now = Date()
        val validMillisSeconds: Long = 3600_000
        val expirationDate = Date(now.time + validMillisSeconds)

        return Jwts.builder()
                .setClaims(Jwts.claims().setSubject(userEntity.username))
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact()
    }

    fun getUsernameFromRequest(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authentication")
        if (authHeader.length < HEADER_TOKEN_INDEX) return null
        val token = authHeader.substring(HEADER_TOKEN_INDEX)
        val username = getUsernameFromToken(token) ?: return null
        return userRepository.findFirstByUsername(username)?.username
    }

    private fun getUsernameFromToken(token: String?): String? {
        return try {
            val claims: Jws<Claims> = Jwts.parser().setSigningKey("secret").parseClaimsJws(token)
            if (claims.body.expiration.after(Date())) {
                claims.body.subject
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
