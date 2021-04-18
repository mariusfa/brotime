package com.fagerland.brotime.integrations

import com.fagerland.brotime.dto.requests.InsertTimeDTO
import com.fagerland.brotime.dto.requests.UpdateTimeDTO
import com.fagerland.brotime.dto.requests.UserDTO
import com.fagerland.brotime.dto.responses.TimeDiffDTO
import com.fagerland.brotime.entities.TimeEntity
import com.fagerland.brotime.services.TimeService
import com.fagerland.brotime.services.UserService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TimeIntTests(
    @Autowired val mockMvc: MockMvc,
    @Autowired val userService: UserService,
    @Autowired val timeService: TimeService
) {

    private lateinit var authHeader: String

    @BeforeAll
    fun setup() {
        val user = UserDTO("testUser", "testPassword")
        val token = userService.loginUser(user)
        authHeader = "Bearer $token"
    }

    @Test
    fun `Should get 401`() {
        mockMvc.perform(get("/api/time/all").header("Authentication", "Bearer 123"))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
        mockMvc.perform(get("/api/time/all"))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    fun `Should insert and get time`() {
        val insertTime = InsertTimeDTO(5,"Europe")
        mockMvc.perform(post("/api/time")
            .header("Authentication", authHeader)
            .contentType(MediaType.APPLICATION_JSON)
            .content(Gson().toJson(insertTime).toString()))
            .andExpect(MockMvcResultMatchers.status().isCreated)

        val result = mockMvc.perform(get("/api/time/all")
            .header("Authentication", authHeader))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val content = result.response.contentAsString
        val itemType = object : TypeToken<List<TimeEntity>>() {}.type
        val timeList = Gson().fromJson<List<TimeEntity>>(content, itemType)
        Assertions.assertThat(timeList).hasSize(1)
    }

    @Test
    fun `Should update time`() {
        val insertTime = InsertTimeDTO(5,"Europe")
        mockMvc.perform(post("/api/time")
            .header("Authentication", authHeader)
            .contentType(MediaType.APPLICATION_JSON)
            .content(Gson().toJson(insertTime).toString()))
            .andExpect(MockMvcResultMatchers.status().isCreated)

        val updateTime = UpdateTimeDTO(1, 2, "Europe", 3)
        mockMvc.perform(put("/api/time")
            .header("Authentication", authHeader)
            .contentType(MediaType.APPLICATION_JSON)
            .content(Gson().toJson(updateTime).toString()))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }
}
