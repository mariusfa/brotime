package com.fagerland.brotime.repositories

import com.fagerland.brotime.models.UserEntry
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserEntry, Long> {
    fun findFirstByUsername(username: String?): UserEntry?
}
