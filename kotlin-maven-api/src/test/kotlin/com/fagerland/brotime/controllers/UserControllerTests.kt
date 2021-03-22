package com.fagerland.brotime.controllers

import com.fagerland.brotime.dto.requests.UserDTO
import com.fagerland.brotime.services.JwtService
import com.fagerland.brotime.services.UserService
import com.google.gson.Gson
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [UserController::class])
class UserControllerTests(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var jwtService: JwtService

    @MockkBean
    private lateinit var userService: UserService

    @Test
    fun `When regiser user return user created status`() {
        val user = UserDTO("fakeUser", "fakePassword")

        every { userService.registerUser(any()) } returns true

        val gson = Gson()
        val userJsonString = gson.toJson(user).toString()

        mockMvc.perform(post("/api/user/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJsonString))
            .andExpect(status().isCreated)
    }

    @Test
    fun `When login user return token`() {
        val user = UserDTO("fakeUser", "fakePassword")
        val token = "fake token"
        every { userService.loginUser(any()) } returns token

        val gson = Gson()
        val userJsonString = gson.toJson(user).toString()

        mockMvc.perform(post("/api/user/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJsonString))
            .andExpect(status().isOk)
            .andExpect(content().string(token))
    }
}
