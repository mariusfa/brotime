package com.fagerland.brotime.services

import com.fagerland.brotime.entities.UserEntity
import com.fagerland.brotime.repositories.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import javax.servlet.http.HttpServletRequest

class JwtServiceTests {

    private val userRepository = mockk<UserRepository>()
    private val jwtService = JwtService(userRepository)

    @Test
    fun `When create jwt and validating token return username`() {
        val fakeUser = UserEntity("fakeUser", "fakeHash")
        val resultToken = jwtService.createJwt(fakeUser)
        val resultUsername = jwtService.getUsernameFromToken(resultToken)

        assertThat(resultToken).hasSizeGreaterThan(0)
        assertThat(resultUsername).isEqualTo(fakeUser.username)
    }

    @Test
    fun `When valid request return username`() {
        val validUser = UserEntity("fakeUser", "fakeHash")
        val token = jwtService.createJwt(validUser)
        val bearerToken = "Bearer $token"
        val request = mockk<HttpServletRequest>()
        every { request.getHeader("Authentication") } returns bearerToken
        every { userRepository.findFirstByUsername(validUser.username) } returns validUser

        val validUsername = jwtService.getUsernameFromRequest(request)
        assertThat(validUsername).isEqualTo(validUser.username)
    }

    @Test
    fun `When invalid user with token return null`() {
        val invalidUser = UserEntity("fakeUser", "fakeHash")
        val token = jwtService.createJwt(invalidUser)
        val bearerToken = "Bearer $token"
        val request = mockk<HttpServletRequest>()
        every { request.getHeader("Authentication") } returns bearerToken
        every { userRepository.findFirstByUsername(invalidUser.username) } returns null

        val nullUser = jwtService.getUsernameFromRequest(request)
        assertThat(nullUser).isNull()
    }

    @Test
    fun `When empty header return null`() {
        val request = mockk<HttpServletRequest>()
        every { request.getHeader("Authentication") } returns ""

        val nullUser = jwtService.getUsernameFromRequest(request)
        assertThat(nullUser).isNull()
    }
}
