package com.fagerland.brotime.repositories

import com.fagerland.brotime.models.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserEntity, Long> {
    fun findFirstByUsername(username: String?): UserEntity?
}
