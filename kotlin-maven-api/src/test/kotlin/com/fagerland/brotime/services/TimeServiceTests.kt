package com.fagerland.brotime.services

import com.fagerland.brotime.dto.requests.insertTimeDTO
import com.fagerland.brotime.entities.TimeEntity
import com.fagerland.brotime.entities.UserEntity
import com.fagerland.brotime.repositories.TimeRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@ExtendWith(MockKExtension::class)
class TimeServiceTests {

    @InjectMockKs
    lateinit var timeService: TimeService

    @MockK
    lateinit var timeRepository: TimeRepository

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should test getTimes`() {
        val user = UserEntity("test", "hash", 1)
        val allTimes = listOf(
            TimeEntity(2,3,"Europe", user, 2),
            TimeEntity(1,1,"Europe", user, 1)
        )
        every { timeRepository.findAllByUserEntityIdOrderByStartTimeDesc(any()) } returns allTimes

        val timesResult = timeService.getTimes(user)

        Assertions.assertEquals(allTimes.size, timesResult.size)
        Assertions.assertEquals(allTimes[0].id, timesResult[0].id)
        Assertions.assertEquals(allTimes[0].userEntity.id, timesResult[0].userEntity.id)
    }

    @Test
    fun `should test getLatestTime`() {
        val user = UserEntity("test", "hash", 1)
        val timeEntry = TimeEntity(1,1,"Europe", user, 1)
        every { timeRepository.findFirstByUserEntityIdOrderByStartTimeDesc(any()) } returns timeEntry

        val result = timeService.getLatestTime(user);

        Assertions.assertNotNull(result)
        Assertions.assertEquals(timeEntry.id, result!!.id)
    }

    @Test
    fun `should test insertTime`() {
        val user = UserEntity("test", "hash", 1)
        val timeEntry = TimeEntity(1,1,"Europe", user, 1)
        val registerTimeDTO = insertTimeDTO(1, "Europe")

        val slot = slot<TimeEntity>()

        every { timeRepository.save(capture(slot)) } returns timeEntry

        timeService.insertTime(user, registerTimeDTO)

        Assertions.assertEquals(registerTimeDTO.timeStamp, slot.captured.startTime)
        Assertions.assertEquals(registerTimeDTO.timeStamp, slot.captured.endTime)
        Assertions.assertEquals(registerTimeDTO.timeZone, slot.captured.timeZone)
        Assertions.assertEquals(user.id, slot.captured.userEntity.id)
    }
}
