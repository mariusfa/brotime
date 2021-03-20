package com.fagerland.brotime.services

import com.fagerland.brotime.entities.UserEntity
import com.fagerland.brotime.repositories.UserRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.util.Date

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

    fun getUsernameFromToken(token: String?): String? {
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
