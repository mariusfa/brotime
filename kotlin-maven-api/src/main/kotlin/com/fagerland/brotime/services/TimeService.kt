package com.fagerland.brotime.services

import com.fagerland.brotime.dto.requests.insertTimeDTO
import com.fagerland.brotime.dto.requests.updateTimeDTO
import com.fagerland.brotime.entities.TimeEntity
import com.fagerland.brotime.entities.UserEntity
import com.fagerland.brotime.repositories.TimeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class TimeService @Autowired constructor(
        val timeRepository: TimeRepository
) {
    fun getTimes(userEntity: UserEntity): List<TimeEntity> {
        return timeRepository.findAllByUserEntryIdOrderByStartTimeDesc(userEntity.id)
    }

    fun getLatestTime(userEntity: UserEntity): TimeEntity? {
        return timeRepository.findFirstByUserEntryIdOrderByStartTimeDesc(userEntity.id)
    }

    fun insertTime(userEntity: UserEntity, insertTimeDto: insertTimeDTO) {
        val timeEntry = TimeEntity(insertTimeDto.timeStamp, insertTimeDto.timeStamp, insertTimeDto.timeZone, userEntity)
        timeRepository.save(timeEntry)
    }

    fun updateEndTime(userEntity: UserEntity?, updateTimeDTO: updateTimeDTO): ResponseEntity<String> {
        if (userEntity != null) {
            val timeEntity: TimeEntity? = timeRepository.findFirstByIdAndUserEntryId(updateTimeDTO.id, userEntity.id)
            if (timeEntity != null) {
                timeEntity.startTime = updateTimeDTO.startTime
                timeEntity.endTime = updateTimeDTO.endTime
                timeEntity.timeZone = updateTimeDTO.timeZone
                timeRepository.save(timeEntity)
                return ResponseEntity.status(HttpStatus.OK).build()
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

    fun deleteTimeEntry(userEntity: UserEntity?, timeEntryId: Long): Boolean {
        if (userEntity != null) {
            val timeEntity: TimeEntity? = timeRepository.findFirstByIdAndUserEntryId(timeEntryId, userEntity.id)
            if (timeEntity != null) {
                timeRepository.delete(timeEntity)
                return true
            }
        }
        return false
    }
}