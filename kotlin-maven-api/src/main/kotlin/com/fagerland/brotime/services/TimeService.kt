package com.fagerland.brotime.services

import com.fagerland.brotime.dto.requests.insertTimeDTO
import com.fagerland.brotime.dto.requests.updateTimeDTO
import com.fagerland.brotime.models.TimeEntry
import com.fagerland.brotime.models.UserEntry
import com.fagerland.brotime.repositories.TimeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class TimeService @Autowired constructor(
        val timeRepository: TimeRepository
) {
    fun getTimes(userEntry: UserEntry): List<TimeEntry> {
        return timeRepository.findAllByUserEntryIdOrderByStartTimeDesc(userEntry.id)
    }

    fun getLatestTime(userEntry: UserEntry): TimeEntry? {
        return timeRepository.findFirstByUserEntryIdOrderByStartTimeDesc(userEntry.id)
    }

    fun insertTime(userEntry: UserEntry, insertTimeDto: insertTimeDTO) {
        val timeEntry = TimeEntry(insertTimeDto.timeStamp, insertTimeDto.timeStamp, insertTimeDto.timeZone, userEntry)
        timeRepository.save(timeEntry)
    }

    fun updateEndTime(userEntry: UserEntry?, updateTimeDTO: updateTimeDTO): ResponseEntity<String> {
        if (userEntry != null) {
            val timeEntry: TimeEntry? = timeRepository.findFirstByIdAndUserEntryId(updateTimeDTO.id, userEntry.id)
            if (timeEntry != null) {
                timeEntry.startTime = updateTimeDTO.startTime
                timeEntry.endTime = updateTimeDTO.endTime
                timeEntry.timeZone = updateTimeDTO.timeZone
                timeRepository.save(timeEntry)
                return ResponseEntity.status(HttpStatus.OK).build()
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

    fun deleteTimeEntry(userEntry: UserEntry?, timeEntryId: Long): Boolean {
        if (userEntry != null) {
            val timeEntry: TimeEntry? = timeRepository.findFirstByIdAndUserEntryId(timeEntryId, userEntry.id)
            if (timeEntry != null) {
                timeRepository.delete(timeEntry)
                return true
            }
        }
        return false
    }
}