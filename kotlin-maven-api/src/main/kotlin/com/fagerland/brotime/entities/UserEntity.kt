package com.fagerland.brotime.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class UserEntity(
    var username: String?,
    var hashedPassword: String,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
)
