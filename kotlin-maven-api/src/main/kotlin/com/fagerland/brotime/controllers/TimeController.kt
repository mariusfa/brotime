package com.fagerland.brotime.controllers

import com.fagerland.brotime.forms.DiffDTO
import com.fagerland.brotime.forms.RegisterTimeForm
import com.fagerland.brotime.forms.TimeForm
import com.fagerland.brotime.models.TimeEntry
import com.fagerland.brotime.models.UserEntry
import com.fagerland.brotime.repositories.TimeRepository
import com.fagerland.brotime.repositories.UserRepository
import com.fagerland.brotime.services.TimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
class TimeController @Autowired constructor(
        val userRepository: UserRepository,
        val timeRepository: TimeRepository,
        val timeService: TimeService
) {

    @GetMapping("/api/time/all")
    fun getTimes(authentication: Authentication): List<TimeEntry> {
        val userEntry: UserEntry = getUserEntry(authentication)
        return timeService.getTimes(userEntry)
    }

    @GetMapping("/api/time")
    fun getLatestTime(authentication: Authentication): TimeEntry? {
        val userEntry: UserEntry = getUserEntry(authentication)
        return timeService.getLatestTime(userEntry)
    }

    @PostMapping("/api/time")
    fun postTime(authentication: Authentication, @RequestBody registerTimeForm: RegisterTimeForm): ResponseEntity<String> {
        val userEntry: UserEntry = getUserEntry(authentication)
        return timeService.insertTime(userEntry, registerTimeForm)
    }

    @PutMapping("/api/time")
    fun editTimeEntry(authentication: Authentication, @RequestBody timeForm: TimeForm): ResponseEntity<String> {
        val userEntry: UserEntry = getUserEntry(authentication)
        return timeService.updateEndTime(userEntry,timeForm)
    }

    @DeleteMapping("/api/time/{timeId}")
    fun deleteTimeEntry(authentication: Authentication, @PathVariable timeId: Long): ResponseEntity<String> {
        val userEntry: UserEntry = getUserEntry(authentication)
        return if (timeService.deleteTimeEntry(userEntry, timeId)) {
            ResponseEntity.status(HttpStatus.OK).build()
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @GetMapping("/api/time/diff")
    fun getDiff(authentication: Authentication): DiffDTO {
        val userEntry: UserEntry = getUserEntry(authentication)
        var timeDiff: Long = 0
        val timeEntries: List<TimeEntry> = timeRepository.findAllByUserEntryIdOrderByStartTimeDesc(userEntry.id)
        if (timeEntries.isNotEmpty()) {
            for (item in timeEntries) {
                timeDiff += item.endTime!! - item.startTime!! - 8 * 3600_000
            }
        }
        return DiffDTO(timeDiff)
    }

    private fun getUserEntry(authentication: Authentication): UserEntry {
        val name: String = authentication.name
        return userRepository.findFirstByUsername(name)!!
    }
}
