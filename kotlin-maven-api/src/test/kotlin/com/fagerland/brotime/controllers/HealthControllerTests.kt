package com.fagerland.brotime.controllers

import com.fagerland.brotime.services.JwtService
import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [HealthController::class])
class HealthControllerTests(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var jwtService: JwtService

    @Test
    fun `Check health path`() {
        mockMvc.perform(get("/api/health"))
            .andExpect(status().isOk)
            .andExpect(content().string("Healthy"))
    }
}
