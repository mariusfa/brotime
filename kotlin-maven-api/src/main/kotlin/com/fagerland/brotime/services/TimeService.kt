package com.fagerland.brotime.services

import com.fagerland.brotime.dto.requests.InsertTimeDTO
import com.fagerland.brotime.dto.requests.UpdateTimeDTO
import com.fagerland.brotime.entities.TimeEntity
import com.fagerland.brotime.entities.UserEntity
import com.fagerland.brotime.repositories.TimeRepository
import org.springframework.stereotype.Service

@Service
class TimeService(
        val timeRepository: TimeRepository
) {
    fun getTimes(userEntity: UserEntity): List<TimeEntity> {
        return timeRepository.findAllByUserEntityIdOrderByStartTimeDesc(userEntity.id)
    }

    fun getLatestTime(userEntity: UserEntity): TimeEntity? {
        return timeRepository.findFirstByUserEntityIdOrderByStartTimeDesc(userEntity.id)
    }

    fun insertTime(userEntity: UserEntity, insertTimeDto: InsertTimeDTO) {
        val timeEntry = TimeEntity(insertTimeDto.timeStamp, insertTimeDto.timeStamp, insertTimeDto.timeZone, userEntity)
        timeRepository.save(timeEntry)
    }

    fun updateTime(userEntity: UserEntity, updateTimeDTO: UpdateTimeDTO): Boolean {
        val timeEntity: TimeEntity? = timeRepository.findFirstByIdAndUserEntityId(updateTimeDTO.id, userEntity.id)
        return if (timeEntity != null) {
            timeEntity.startTime = updateTimeDTO.startTime
            timeEntity.endTime = updateTimeDTO.endTime
            timeEntity.timeZone = updateTimeDTO.timeZone
            timeRepository.save(timeEntity)
            true
        } else {
            false
        }
    }

    fun deleteTimeEntity(userEntity: UserEntity, timeEntryId: Long): Boolean {
        val timeEntry: TimeEntity? = timeRepository.findFirstByIdAndUserEntityId(timeEntryId, userEntity.id)
        return if (timeEntry != null) {
            timeRepository.delete(timeEntry)
            true
        } else {
            false
        }
    }

    fun getTimeDiff(userEntityId: Long): Long {
        var timeDiff: Long = 0
        val timeEntries: List<TimeEntity> = timeRepository.findAllByUserEntityIdOrderByStartTimeDesc(userEntityId)
        if (timeEntries.isNotEmpty()) {
            for (item in timeEntries) {
                timeDiff += item.endTime!! - item.startTime!! - 8 * 3600_000
            }
        }
        return timeDiff
    }
}