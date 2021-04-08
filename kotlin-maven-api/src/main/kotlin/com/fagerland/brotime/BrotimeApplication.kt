package com.fagerland.brotime

import com.fagerland.brotime.dto.requests.UserDTO
import com.fagerland.brotime.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@SpringBootApplication
class BrotimeApplication

fun main(args: Array<String>) {
	runApplication<BrotimeApplication>(*args)
}


@Component
@Profile("test")
class TestDataRunner (
	@Autowired val userService: UserService
) : CommandLineRunner {

	override fun run(vararg args: String?) {
		val testUser = UserDTO("testUser", "testPassword")
		userService.registerUser(testUser)
	}
}
