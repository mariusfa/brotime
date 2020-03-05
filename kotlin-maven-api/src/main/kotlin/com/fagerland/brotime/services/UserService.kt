package com.fagerland.brotime.services

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService {

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