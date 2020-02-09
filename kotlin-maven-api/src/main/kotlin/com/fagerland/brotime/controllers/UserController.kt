package com.fagerland.brotime.controllers

import com.fagerland.brotime.forms.LoginForm
import com.fagerland.brotime.models.UserEntry
import com.fagerland.brotime.repositories.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@CrossOrigin
@RestController
class UserController @Autowired constructor(
        val userRepository: UserRepository
) {

    @PostMapping("/api/user/register")
    fun registerUser(@RequestBody loginForm: LoginForm): ResponseEntity<String> {
        val userEntry: UserEntry? = userRepository.findFirstByUsername(loginForm.username)
        if (userEntry == null) {
            val passwordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()
            val hashedPassword: String = passwordEncoder.encode(loginForm.password)
            val newUserEntry: UserEntry = UserEntry(loginForm.username, hashedPassword);
            userRepository.save(newUserEntry)
            return ResponseEntity.status(HttpStatus.CREATED).build()
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/api/user/login")
    fun loginUser(@RequestBody loginForm: LoginForm): ResponseEntity<String> {
        val userEntry: UserEntry? = userRepository.findFirstByUsername(loginForm.username)
        if (userEntry == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        val passwordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()
        val passwordMatch: Boolean = passwordEncoder.matches(loginForm.password, userEntry.hashedPassword)

        if (passwordMatch && userEntry.username.equals(loginForm.username)) {
            val token: String = getJwtToken(userEntry)
            return ResponseEntity.ok(token)
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
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