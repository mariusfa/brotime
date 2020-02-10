package com.fagerland.brotime.controllers

import com.fagerland.brotime.forms.DiffDTO
import com.fagerland.brotime.forms.EndTimeForm
import com.fagerland.brotime.forms.RegisterTimeForm
import com.fagerland.brotime.forms.TimeForm
import com.fagerland.brotime.models.TimeEntry
import com.fagerland.brotime.models.UserEntry
import com.fagerland.brotime.repositories.TimeRepository
import com.fagerland.brotime.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
class TimeController @Autowired constructor(
        val userRepository: UserRepository,
        val timeRepository: TimeRepository
){
    @GetMapping("/api/time/all")
    fun getTimes(authentication: Authentication): List<TimeEntry> {
        val userEntry: UserEntry? = getUserEntry(authentication)
        if (userEntry != null) {
            return timeRepository.findAllByUserEntryId(userEntry.id)
        }
        return emptyList()
    }

    @PostMapping("/api/time")
    fun postTime(authentication: Authentication, @RequestBody registerTimeForm: RegisterTimeForm): ResponseEntity<String> {
        val userEntry: UserEntry? = getUserEntry(authentication)
        if (userEntry != null) {
            val timeEntry: TimeEntry = TimeEntry(registerTimeForm.timeStamp, registerTimeForm.timeStamp, registerTimeForm.timeZone, userEntry)
            timeRepository.save(timeEntry)
            return ResponseEntity.status(HttpStatus.CREATED).build()
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

    @PutMapping("/api/time/endtime")
    fun updateEndTime(authentication: Authentication, @RequestBody endTimeForm: EndTimeForm): ResponseEntity<String> {
        val userEntry: UserEntry? = getUserEntry(authentication)
        if (userEntry != null) {
            val timeEntry: TimeEntry? = timeRepository.findFirstByUserEntryIdOrderByStartTimeDesc(userEntry.id)
            if (timeEntry != null) {
                timeEntry.endTime = endTimeForm.endTime
                timeRepository.save(timeEntry)
                return ResponseEntity.status(HttpStatus.OK).build()
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

    @PutMapping("/api/time")
    fun editTimeEntry(authentication: Authentication, @RequestBody timeForm: TimeForm): ResponseEntity<String> {
        val userEntry: UserEntry? = getUserEntry(authentication)
        if (userEntry != null) {
            val timeEntry: TimeEntry? = timeRepository.findFirstByIdAndByUserEntryId(timeForm.id, userEntry.id)
            if (timeEntry != null) {
                timeEntry.startTime = timeForm.startTime
                timeEntry.endTime = timeForm.endTime
                timeEntry.timeZone = timeForm.timeZone
                return ResponseEntity.status(HttpStatus.OK).build()
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

    @DeleteMapping("/api/time/{timeId}")
    fun deleteTimeEntry(authentication: Authentication, @PathVariable timeId: Long): ResponseEntity<String> {
        val userEntry: UserEntry? = getUserEntry(authentication)
        if (userEntry != null) {
            val timeEntry: TimeEntry? = timeRepository.findFirstByIdAndByUserEntryId(timeId, userEntry.id)
           if (timeEntry != null) {
               timeRepository.delete(timeEntry)
               return ResponseEntity.status(HttpStatus.OK).build()
           }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

    @GetMapping("/api/time/diff")
    fun getDiff(authentication: Authentication): DiffDTO {
        val userEntry: UserEntry? = getUserEntry(authentication)
        var timeDiff: Long = 0
        if  (userEntry != null) {
            val timeEntries: List<TimeEntry> = timeRepository.findAllByUserEntryId(userEntry.id)
            if (timeEntries.size > 0) {
                for (item in timeEntries) {
                    timeDiff += item.endTime!! - item.startTime!!
                }
                timeDiff = timeDiff/timeEntries.size
            }
        }
        return DiffDTO(timeDiff)
    }

    private fun getUserEntry(authentication: Authentication): UserEntry? {
        val name: String = authentication.name
        val userEntry: UserEntry? = userRepository.findFirstByUsername(name)
        return userEntry
    }


}