package com.fagerland.brotime.controllers

import com.fagerland.brotime.dto.requests.InsertTimeDTO
import com.fagerland.brotime.dto.requests.UpdateTimeDTO
import com.fagerland.brotime.dto.responses.TimeDiffDTO
import com.fagerland.brotime.entities.TimeEntity
import com.fagerland.brotime.entities.UserEntity
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
class TimeController(
    val userRepository: UserRepository,
    val timeService: TimeService
) {

    @GetMapping("/api/time/all")
    fun getTimes(authentication: Authentication): List<TimeEntity> {
        val userEntity: UserEntity = getUserEntity(authentication)
        return timeService.getTimes(userEntity)
    }

    @GetMapping("/api/time")
    fun getLatestTime(authentication: Authentication): TimeEntity? {
        val userEntity: UserEntity = getUserEntity(authentication)
        return timeService.getLatestTime(userEntity)
    }

    @PostMapping("/api/time")
    fun postTime(authentication: Authentication, @RequestBody insertTimeDTO: InsertTimeDTO): ResponseEntity<String> {
        val userEntity: UserEntity = getUserEntity(authentication)
        timeService.insertTime(userEntity, insertTimeDTO)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PutMapping("/api/time")
    fun updateTime(authentication: Authentication, @RequestBody updateTimeDTO: UpdateTimeDTO): ResponseEntity<String> {
        val userEntity: UserEntity = getUserEntity(authentication)
        return if (timeService.updateTime(userEntity, updateTimeDTO)) {
            ResponseEntity.status(HttpStatus.OK).build()
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    @DeleteMapping("/api/time/{timeId}")
    fun deleteTimeEntry(authentication: Authentication, @PathVariable timeId: Long): ResponseEntity<String> {
        val userEntity: UserEntity = getUserEntity(authentication)
        return if (timeService.deleteTimeEntity(userEntity, timeId)) {
            ResponseEntity.status(HttpStatus.OK).build()
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    @GetMapping("/api/time/diff")
    fun getTimeDiff(authentication: Authentication):TimeDiffDTO {
        val userEntry: UserEntity = getUserEntity(authentication)
        return TimeDiffDTO(timeService.getTimeDiff(userEntry.id!!))
    }

    private fun getUserEntity(authentication: Authentication): UserEntity {
        val name: String = authentication.name
        return userRepository.findFirstByUsername(name)!!
    }
}
