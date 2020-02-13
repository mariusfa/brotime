package com.fagerland.brotime.repositories

import com.fagerland.brotime.models.TimeEntry
import org.springframework.data.repository.CrudRepository

interface TimeRepository : CrudRepository<TimeEntry, Long> {
    fun findAllByUserEntryId(userId: Long?): List<TimeEntry>
    fun findFirstByUserEntryIdOrderByStartTimeDesc(userId: Long?): TimeEntry
    fun findFirstByIdAndUserEntryId(timeId: Long?, userId: Long?): TimeEntry
}