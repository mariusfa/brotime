package com.fagerland.brotime.repositories

import com.fagerland.brotime.entities.TimeEntity
import org.springframework.data.repository.CrudRepository

interface TimeRepository : CrudRepository<TimeEntity, Long> {
    fun findAllByUserEntryIdOrderByStartTimeDesc(userId: Long?): List<TimeEntity>
    fun findFirstByUserEntryIdOrderByStartTimeDesc(userId: Long?): TimeEntity?
    fun findFirstByIdAndUserEntryId(timeId: Long?, userId: Long?): TimeEntity?
}
