package com.fagerland.brotime.services

import com.fagerland.brotime.dto.requests.UserDTO
import com.fagerland.brotime.entities.UserEntity
import com.fagerland.brotime.repositories.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserServiceTests {

    private val userRepository = mockk<UserRepository>()
    private val userService = UserService(userRepository)

    @Test
    fun `should test registerUser`() {
        every { userRepository.findFirstByUsername("test") } returns null
        every { userRepository.save(ofType(UserEntity::class)) } returns UserEntity("test", "test")

        val result = userService.registerUser(UserDTO("test", "test"))
        assertThat(result).isTrue
    }

    @Test
    fun `should test loginUser`() {
        val userEntry = UserEntity("test", "\$2y\$10\$W30zmvn57Cs6Hxu00jhkAOiirdgkrDyrz0ro6tfsFnhC5Ka510Nfu")
        val userDTO = UserDTO("test", "test")
        every { userRepository.findFirstByUsername("test") } returns userEntry

        val result = userService.loginUser(userDTO)
        assertThat(result).isNotNull
    }

    @Test
    fun `should test getUsernameFromToken`() {
        val userEntry = UserEntity("test", "\$2y\$10\$W30zmvn57Cs6Hxu00jhkAOiirdgkrDyrz0ro6tfsFnhC5Ka510Nfu")
        val userDTO = UserDTO("test", "test")
        every { userRepository.findFirstByUsername("test") } returns userEntry
        val token = userService.loginUser(userDTO)

        val result = userService.getUsernameFromToken(token)
        assertThat(result).isEqualTo("test")
    }
}
