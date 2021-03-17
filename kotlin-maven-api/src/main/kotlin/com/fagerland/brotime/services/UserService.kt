package com.fagerland.brotime.services

import com.fagerland.brotime.dto.requests.UserDTO
import com.fagerland.brotime.models.UserEntity
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

    fun registerUser(userDTO: UserDTO): Boolean {
        if (userRepository.findFirstByUsername(userDTO.username) != null) {
            return false
        }
        val hashedPassword: String = passwordEncoder.encode(userDTO.password)
        val newUserEntry = UserEntity(userDTO.username, hashedPassword)
        userRepository.save(newUserEntry)
        return true
    }

    fun loginUser(userDTO: UserDTO): String? {
        val existingUser = userRepository.findFirstByUsername(userDTO.username)
        if (existingUser != null && isCredentialsValid(userDTO, existingUser)) {
            return getJwtToken(existingUser)
        }
        return null
    }

    private fun isCredentialsValid(userDTO: UserDTO, existingUser: UserEntity) =
        passwordEncoder.matches(userDTO.password, existingUser.hashedPassword) && existingUser.username.equals(userDTO.username)

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

    private fun getJwtToken(userEntity: UserEntity): String {
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
}