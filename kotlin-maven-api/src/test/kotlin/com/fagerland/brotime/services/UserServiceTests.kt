package com.fagerland.brotime.services

import com.fagerland.brotime.dto.requests.UserDTO
import com.fagerland.brotime.entities.UserEntity
import com.fagerland.brotime.repositories.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@ExtendWith(MockKExtension::class)
class UserServiceTests {

    @InjectMockKs
    lateinit var userService: UserService

    @MockK
    lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `should test registerUser`() {
        every { userRepository.findFirstByUsername("test") } returns null
        every { userRepository.save(ofType(UserEntity::class)) } returns UserEntity("test", "test")

        Assertions.assertTrue(userService.registerUser(UserDTO("test", "test")))
    }

    @Test
    fun `should test loginUser`() {
        val userEntry = UserEntity("test", "\$2y\$10\$W30zmvn57Cs6Hxu00jhkAOiirdgkrDyrz0ro6tfsFnhC5Ka510Nfu")
        val userDTO = UserDTO("test", "test")
        every { userRepository.findFirstByUsername("test") } returns userEntry

        Assertions.assertNotNull(userService.loginUser(userDTO))
    }

    @Test
    fun `should test getUsernameFromToken`() {
        val userEntry = UserEntity("test", "\$2y\$10\$W30zmvn57Cs6Hxu00jhkAOiirdgkrDyrz0ro6tfsFnhC5Ka510Nfu")
        val userDTO = UserDTO("test", "test")
        every { userRepository.findFirstByUsername("test") } returns userEntry
        val token = userService.loginUser(userDTO)

        Assertions.assertEquals("test", userService.getUsernameFromToken(token))
    }
}
