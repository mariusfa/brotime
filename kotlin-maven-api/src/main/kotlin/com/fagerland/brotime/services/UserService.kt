package com.fagerland.brotime.services

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService {

    fun validateToken(bearerToken: String?): Boolean {
        return try {
            val token = bearerToken?.substring(7, bearerToken.length)
            val claims: Jws<Claims> = Jwts.parser().setSigningKey("secret").parseClaimsJws(token)
            claims.body.expiration.after(Date())
        } catch (e: Exception) {
            false
        }
    }
}