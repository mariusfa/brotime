package com.fagerland.brotime.repositories

import com.fagerland.brotime.entities.TimeEntity
import org.springframework.data.repository.CrudRepository

interface TimeRepository : CrudRepository<TimeEntity, Long> {
    fun findAllByUserEntityIdOrderByStartTimeDesc(userId: Long?): List<TimeEntity>
    fun findFirstByUserEntityIdOrderByStartTimeDesc(userId: Long?): TimeEntity?
    fun findFirstByIdAndUserEntityId(timeId: Long?, userId: Long?): TimeEntity?
}
