package com.fagerland.brotime.controllers

import com.fagerland.brotime.models.TimeEntry
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import kotlin.random.Random

private const val ONE_HOUR = 3600_000

@CrossOrigin
@RestController
class MockController {

    @GetMapping("/mock/time")
    fun getTime(): List<TimeEntry> {
        var timeList = mutableListOf<TimeEntry>()
        val timeZone = "Europe/Berlin"

        for (i in 0 until 30) {
            val startOfDay = LocalDateTime.now().with(LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - 24*ONE_HOUR*i
            val workStart = startOfDay + ONE_HOUR*7 + Random.nextLong((ONE_HOUR*2).toLong())
            val workEnd = workStart + ONE_HOUR*7 + Random.nextLong((ONE_HOUR*2).toLong())
            val newEntry = TimeEntry(workStart, workEnd, timeZone);
            timeList.add(newEntry)

        }
        return timeList
    }
}