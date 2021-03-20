package com.fagerland.brotime.services

import com.fagerland.brotime.entities.UserEntity
import com.fagerland.brotime.repositories.UserRepository
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ABCServiceTests {

    private val userRepository = mockk<UserRepository>()
    private val jwtService = ABCService(userRepository)

    @Test
    fun `When create jwt and validating token return username`() {
        val fakeUser = UserEntity("fakeUser", "fakeHash")
        val resultToken = jwtService.createJwt(fakeUser)
        val resultUsername = jwtService.getUsernameFromToken(resultToken)

        assertThat(resultToken).hasSizeGreaterThan(0)
        assertThat(resultUsername).isEqualTo(fakeUser.username)
    }



}