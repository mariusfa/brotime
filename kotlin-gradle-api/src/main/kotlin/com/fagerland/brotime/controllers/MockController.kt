package com.fagerland.brotime.controllers

import com.fagerland.brotime.models.TimeEntry
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.ZoneId

private const val ONE_HOUR = 3600_000

@CrossOrigin
@RestController
class MockController {


    @GetMapping("/mock/time")
    fun getTime(): List<TimeEntry> {
        var timeList = mutableListOf<TimeEntry>()

        val currentTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val te = TimeEntry(currentTime, currentTime, ZoneId.systemDefault().toString())
        timeList.add(te)
        return timeList
    }
}