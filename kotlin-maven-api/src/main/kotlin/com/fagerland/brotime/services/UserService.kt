package com.fagerland.brotime.services

import com.fagerland.brotime.forms.LoginForm
import com.fagerland.brotime.models.UserEntry
import com.fagerland.brotime.repositories.UserRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService @Autowired constructor(
    val userRepository: UserRepository
) {
    val passwordEncoder = BCryptPasswordEncoder()

    fun registerUser(loginForm: LoginForm): Boolean {
        if (userRepository.findFirstByUsername(loginForm.username) != null) {
            return false
        }
        val hashedPassword: String = passwordEncoder.encode(loginForm.password)
        val newUserEntry = UserEntry(loginForm.username, hashedPassword)
        userRepository.save(newUserEntry)
        return true
    }

    fun loginUser(loginForm: LoginForm): String? {
        val existingUser = userRepository.findFirstByUsername(loginForm.username)
        if (existingUser != null && isCredentialsValid(loginForm, existingUser)) {
            return getJwtToken(existingUser)
        }
        return null
    }

    private fun isCredentialsValid(loginForm: LoginForm, existingUser: UserEntry) =
        passwordEncoder.matches(loginForm.password, existingUser.hashedPassword) && existingUser.username.equals(loginForm.username)

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

    private fun getJwtToken(userEntry: UserEntry): String {
        val now = Date()
        val validMillisSeconds: Long = 3600_000
        val expirationDate = Date(now.time + validMillisSeconds)

        return Jwts.builder()
            .setClaims(Jwts.claims().setSubject(userEntry.username))
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS256, "secret")
            .compact()
    }
}