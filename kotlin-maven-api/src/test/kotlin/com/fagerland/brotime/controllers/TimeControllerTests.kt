package com.fagerland.brotime.controllers

import com.fagerland.brotime.dto.requests.InsertTimeDTO
import com.fagerland.brotime.entities.TimeEntity
import com.fagerland.brotime.entities.UserEntity
import com.fagerland.brotime.repositories.UserRepository
import com.fagerland.brotime.services.JwtService
import com.fagerland.brotime.services.TimeService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [TimeController::class])
class TimeControllerTests(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var jwtService: JwtService

    @MockkBean
    private lateinit var timeService: TimeService

    @MockkBean
    private lateinit var userRepository: UserRepository

    private val user = UserEntity("fakeUser", "fakeHash")

    @Test
    fun `When getTimes returns TimeEntity list`() {
        val timeEntityList = listOf(
            TimeEntity(0, 1, null, user),
            TimeEntity(2, 3, null, user),
            TimeEntity(3, 4, null, user)
        )

        every { jwtService.getUsernameFromRequest(any()) } returns user.username
        every { userRepository.findFirstByUsername(user.username) } returns user
        every { timeService.getTimes(user) } returns timeEntityList

        val result = mockMvc.perform(get("/api/time/all"))
            .andExpect(status().isOk)
            .andReturn()

        val content = result.response.contentAsString
        val itemType = object : TypeToken<List<TimeEntity>>() {}.type
        val timeList = Gson().fromJson<List<TimeEntity>>(content, itemType)
        assertThat(timeList).hasSize(timeEntityList.size)
    }

    @Test
    fun `When getLatestTime return latest time entity`() {
        val latestTime = TimeEntity(2, 3, null, user)
        every { jwtService.getUsernameFromRequest(any()) } returns user.username
        every { userRepository.findFirstByUsername(user.username) } returns user
        every { timeService.getLatestTime(user) } returns latestTime

        val result = mockMvc.perform(get("/api/time"))
            .andExpect(status().isOk)
            .andReturn()

        val content = result.response.contentAsString
        val itemType = object : TypeToken<TimeEntity>() {}.type
        val timeResult = Gson().fromJson<TimeEntity>(content, itemType)
        assertThat(timeResult.startTime).isEqualTo(latestTime.startTime)
        assertThat(timeResult.endTime).isEqualTo(latestTime.endTime)
    }

    @Test
    fun `When postTime return created status`() {
        val inserTime = InsertTimeDTO(5,"Europe")
        every { jwtService.getUsernameFromRequest(any()) } returns user.username
        every { userRepository.findFirstByUsername(user.username) } returns user

        val slot = slot<InsertTimeDTO>()

        every { timeService.insertTime(user, capture(slot)) } returns Unit

        mockMvc.perform(post("/api/time")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Gson().toJson(inserTime).toString()))
            .andExpect(status().isCreated)

        assertThat(slot.captured.timeStamp).isEqualTo(inserTime.timeStamp)
        assertThat(slot.captured.timeZone).isEqualTo(inserTime.timeZone)
    }
}
