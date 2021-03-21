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
    private val request = mockk<HttpServletRequest>()
    private val jwtService = JwtService(userRepository)
    private val fakeUser = UserEntity("fakeUser", "fakeHash")

    @Test
    fun `When valid request return username`() {
        val token = jwtService.createJwt(fakeUser)
        val bearerToken = "Bearer $token"
        every { request.getHeader("Authentication") } returns bearerToken
        every { userRepository.findFirstByUsername(fakeUser.username) } returns fakeUser

        val validUsername = jwtService.getUsernameFromRequest(request)
        assertThat(validUsername).isEqualTo(fakeUser.username)
    }

    @Test
    fun `When invalid user with token return null`() {
        val token = jwtService.createJwt(fakeUser)
        val bearerToken = "Bearer $token"
        every { request.getHeader("Authentication") } returns bearerToken
        every { userRepository.findFirstByUsername(fakeUser.username) } returns null

        val nullUser = jwtService.getUsernameFromRequest(request)
        assertThat(nullUser).isNull()
    }

    @Test
    fun `When invalid auth header too small return null`() {
        val garbageHeader = "..."
        every { request.getHeader("Authentication") } returns garbageHeader

        val nullUser = jwtService.getUsernameFromRequest(request)
        assertThat(nullUser).isNull()
    }

    @Test
    fun `When invalid auth header return null`() {
        val fakeToken = "Bearer fake"
        every { request.getHeader("Authentication") } returns fakeToken

        val nullUser = jwtService.getUsernameFromRequest(request)
        assertThat(nullUser).isNull()
    }
}
