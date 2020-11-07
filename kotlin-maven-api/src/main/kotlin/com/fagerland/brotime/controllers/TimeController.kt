package com.fagerland.brotime.controllers

import com.fagerland.brotime.dto.requests.InsertTimeDTO
import com.fagerland.brotime.dto.requests.UpdateTimeDTO
import com.fagerland.brotime.dto.responses.TimeDiffDTO
import com.fagerland.brotime.models.TimeEntry
import com.fagerland.brotime.models.UserEntry
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
    fun postTime(authentication: Authentication, @RequestBody insertTimeDTO: InsertTimeDTO): ResponseEntity<String> {
        val userEntry: UserEntry = getUserEntry(authentication)
        timeService.insertTime(userEntry, insertTimeDTO)
        return ResponseEntity.status(HttpStatus.CREATED).build()

    }

    @PutMapping("/api/time")
    fun updateTime(authentication: Authentication, @RequestBody updateTimeDTO: UpdateTimeDTO): ResponseEntity<String> {
        val userEntry: UserEntry = getUserEntry(authentication)
        return if (timeService.updateTime(userEntry, updateTimeDTO)) {
            ResponseEntity.status(HttpStatus.OK).build()
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    @DeleteMapping("/api/time/{timeId}")
    fun deleteTimeEntry(authentication: Authentication, @PathVariable timeId: Long): ResponseEntity<String> {
        val userEntry: UserEntry = getUserEntry(authentication)
        return if (timeService.deleteTime(userEntry, timeId)) {
            ResponseEntity.status(HttpStatus.OK).build()
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    @GetMapping("/api/time/diff")
    fun getTimeDiff(authentication: Authentication):TimeDiffDTO {
        val userEntry: UserEntry = getUserEntry(authentication)
        return TimeDiffDTO(timeService.getTimeDiff(userEntry.id!!))
    }

    private fun getUserEntry(authentication: Authentication): UserEntry {
        val name: String = authentication.name
        return userRepository.findFirstByUsername(name)!!
    }
}
