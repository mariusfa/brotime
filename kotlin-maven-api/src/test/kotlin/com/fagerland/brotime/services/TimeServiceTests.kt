package com.fagerland.brotime.services

import com.fagerland.brotime.repositories.TimeRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
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
    fun `should test getTimes`
}