package com.fagerland.brotime.controllers

import com.fagerland.brotime.dto.requests.TokenDTO
import com.fagerland.brotime.dto.requests.UserDTO
import com.fagerland.brotime.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
class UserController @Autowired constructor(
        val userService: UserService
) {

    @PostMapping("/api/user/register")
    fun registerUser(@RequestBody userDTO: UserDTO): ResponseEntity<String> {
        return if (userService.registerUser(userDTO)) {
            ResponseEntity.status(HttpStatus.CREATED).build()
        } else {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }

    @PostMapping("/api/user/login")
    fun loginUser(@RequestBody userDTO: UserDTO): ResponseEntity<String> {
        val token = userService.loginUser(userDTO)
        return if (token != null) {
            ResponseEntity.ok(token)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @PostMapping("/api/user/validate")
    fun validateToken(@RequestBody tokenDTO: TokenDTO): Boolean =
        userService.getUsernameFromToken(tokenDTO.token) != null
}
