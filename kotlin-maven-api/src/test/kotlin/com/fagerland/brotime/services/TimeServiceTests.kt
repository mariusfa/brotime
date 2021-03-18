package com.fagerland.brotime.services

import com.fagerland.brotime.dto.requests.InsertTimeDTO
import com.fagerland.brotime.entities.TimeEntity
import com.fagerland.brotime.entities.UserEntity
import com.fagerland.brotime.repositories.TimeRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TimeServiceTests {

    private val timeRepository = mockk<TimeRepository>()
    private val timeService = TimeService(timeRepository)

    @Test
    fun `should test getTimes`() {
        val user = UserEntity("test", "hash", 1)
        val allTimes = listOf(
            TimeEntity(2, 3, "Europe", user, 2),
            TimeEntity(1, 1, "Europe", user, 1)
        )
        every { timeRepository.findAllByUserEntityIdOrderByStartTimeDesc(any()) } returns allTimes

        val timesResult = timeService.getTimes(user)

        assertThat(timesResult).hasSize(allTimes.size)
        assertThat(allTimes[0].id).isEqualTo(timesResult[0].id)
        assertThat(allTimes[0].userEntity.id).isEqualTo(timesResult[0].userEntity.id)
    }

    @Test
    fun `should test getLatestTime`() {
        val user = UserEntity("test", "hash", 1)
        val timeEntry = TimeEntity(1, 1, "Europe", user, 1)
        every { timeRepository.findFirstByUserEntityIdOrderByStartTimeDesc(any()) } returns timeEntry

        val result = timeService.getLatestTime(user)

        assertThat(result).isNotNull
        assertThat(result!!.id).isEqualTo(timeEntry.id)
    }

    @Test
    fun `should test insertTime`() {
        val user = UserEntity("test", "hash", 1)
        val timeEntry = TimeEntity(1, 1, "Europe", user, 1)
        val registerTimeDTO = InsertTimeDTO(1, "Europe")

        val slot = slot<TimeEntity>()

        every { timeRepository.save(capture(slot)) } returns timeEntry

        timeService.insertTime(user, registerTimeDTO)

        assertThat(registerTimeDTO.timeStamp).isEqualTo(slot.captured.startTime)
        assertThat(registerTimeDTO.timeStamp).isEqualTo(slot.captured.endTime)
        assertThat(registerTimeDTO.timeZone).isEqualTo(slot.captured.timeZone)
        assertThat(user.id).isEqualTo(slot.captured.userEntity.id)
    }
}
