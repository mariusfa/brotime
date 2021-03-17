package com.fagerland.brotime.controllers

import com.fagerland.brotime.dto.requests.insertTimeDTO
import com.fagerland.brotime.dto.requests.updateTimeDTO
import com.fagerland.brotime.dto.responses.DiffDTO
import com.fagerland.brotime.models.TimeEntity
import com.fagerland.brotime.models.UserEntity
import com.fagerland.brotime.repositories.TimeRepository
import com.fagerland.brotime.repositories.UserRepository
import com.fagerland.brotime.services.TimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
class TimeController @Autowired constructor(
    val userRepository: UserRepository,
    val timeRepository: TimeRepository,
    val timeService: TimeService
) {

    @GetMapping("/api/time/all")
    fun getTimes(authentication: Authentication): List<TimeEntity> {
        val userEntity: UserEntity = getUserEntry(authentication)
        return timeService.getTimes(userEntity)
    }

    @GetMapping("/api/time")
    fun getLatestTime(authentication: Authentication): TimeEntity? {
        val userEntity: UserEntity = getUserEntry(authentication)
        return timeService.getLatestTime(userEntity)
    }

    @PostMapping("/api/time")
    fun postTime(authentication: Authentication, @RequestBody insertTimeDTO: insertTimeDTO): ResponseEntity<String> {
        val userEntity: UserEntity = getUserEntry(authentication)
        timeService.insertTime(userEntity, insertTimeDTO)
        return ResponseEntity.status(HttpStatus.CREATED).build()

    }

    @PutMapping("/api/time")
    fun editTimeEntry(authentication: Authentication, @RequestBody updateTimeDTO: updateTimeDTO): ResponseEntity<String> {
        val userEntity: UserEntity = getUserEntry(authentication)
        return timeService.updateEndTime(userEntity, updateTimeDTO)
    }

    @DeleteMapping("/api/time/{timeId}")
    fun deleteTimeEntry(authentication: Authentication, @PathVariable timeId: Long): ResponseEntity<String> {
        val userEntity: UserEntity = getUserEntry(authentication)
        return if (timeService.deleteTimeEntry(userEntity, timeId)) {
            ResponseEntity.status(HttpStatus.OK).build()
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @GetMapping("/api/time/diff")
    fun getDiff(authentication: Authentication): DiffDTO {
        val userEntity: UserEntity = getUserEntry(authentication)
        var timeDiff: Long = 0
        val timeEntities: List<TimeEntity> = timeRepository.findAllByUserEntryIdOrderByStartTimeDesc(userEntity.id)
        if (timeEntities.isNotEmpty()) {
            for (item in timeEntities) {
                timeDiff += item.endTime!! - item.startTime!! - 8 * 3600_000
            }
        }
        return DiffDTO(timeDiff)
    }

    private fun getUserEntry(authentication: Authentication): UserEntity {
        val name: String = authentication.name
        return userRepository.findFirstByUsername(name)!!
    }
}
